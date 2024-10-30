package dev.luvbeeq.baritone.cache;

import dev.luvbeeq.baritone.api.cache.ICachedWorld;
import dev.luvbeeq.baritone.api.cache.IWorldScanner;
import dev.luvbeeq.baritone.api.utils.BetterBlockPos;
import dev.luvbeeq.baritone.api.utils.BlockOptionalMetaLookup;
import dev.luvbeeq.baritone.api.utils.IPlayerContext;
import dev.luvbeeq.baritone.utils.accessor.IBitArray;
import dev.luvbeeq.baritone.utils.accessor.IPalettedContainer;
import io.netty.buffer.Unpooled;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.BitArray;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.palette.IPalette;
import net.minecraft.util.palette.IdentityPalette;
import net.minecraft.util.palette.PalettedContainer;
import net.minecraft.world.chunk.AbstractChunkProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum FasterWorldScanner implements IWorldScanner {
    INSTANCE;

    @Override
    public List<BlockPos> scanChunkRadius(IPlayerContext ctx, BlockOptionalMetaLookup filter, int max, int yLevelThreshold, int maxSearchRadius) {
        if (maxSearchRadius < 0) {
            throw new IllegalArgumentException("chunkRange must be >= 0");
        }
        return scanChunksInternal(ctx, filter, getChunkRange(ctx.playerFeet().x >> 4, ctx.playerFeet().z >> 4, maxSearchRadius), max);
    }

    @Override
    public List<BlockPos> scanChunk(IPlayerContext ctx, BlockOptionalMetaLookup filter, ChunkPos pos, int max, int yLevelThreshold) {
        Stream<BlockPos> stream = scanChunkInternal(ctx, filter, pos);
        if (max >= 0) {
            stream = stream.limit(max);
        }
        return stream.collect(Collectors.toList());
    }

    @Override
    public int repack(IPlayerContext ctx) {
        return this.repack(ctx, 40);
    }

    @Override
    public int repack(IPlayerContext ctx, int range) {
        AbstractChunkProvider chunkProvider = ctx.world().getChunkProvider();
        ICachedWorld cachedWorld = ctx.worldData().getCachedWorld();

        BetterBlockPos playerPos = ctx.playerFeet();

        int playerChunkX = playerPos.getX() >> 4;
        int playerChunkZ = playerPos.getZ() >> 4;

        int minX = playerChunkX - range;
        int minZ = playerChunkZ - range;
        int maxX = playerChunkX + range;
        int maxZ = playerChunkZ + range;

        int queued = 0;
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                Chunk chunk = chunkProvider.getChunk(x, z, false);

                if (chunk != null && !chunk.isEmpty()) {
                    queued++;
                    cachedWorld.queueForPacking(chunk);
                }
            }
        }

        return queued;
    }

    // ordered in a way that the closest blocks are generally first
    public static List<ChunkPos> getChunkRange(int centerX, int centerZ, int chunkRadius) {
        List<ChunkPos> chunks = new ArrayList<>();
        // spiral out
        chunks.add(new ChunkPos(centerX, centerZ));
        for (int i = 1; i < chunkRadius; i++) {
            for (int j = 0; j <= i; j++) {
                chunks.add(new ChunkPos(centerX - j, centerZ - i));
                if (j != 0) {
                    chunks.add(new ChunkPos(centerX + j, centerZ - i));
                    chunks.add(new ChunkPos(centerX - j, centerZ + i));
                }
                chunks.add(new ChunkPos(centerX + j, centerZ + i));
                if (j != i) {
                    chunks.add(new ChunkPos(centerX - i, centerZ - j));
                    chunks.add(new ChunkPos(centerX + i, centerZ - j));
                    if (j != 0) {
                        chunks.add(new ChunkPos(centerX - i, centerZ + j));
                        chunks.add(new ChunkPos(centerX + i, centerZ + j));
                    }
                }
            }
        }
        return chunks;
    }

    private List<BlockPos> scanChunksInternal(IPlayerContext ctx, BlockOptionalMetaLookup lookup, List<ChunkPos> chunkPositions, int maxBlocks) {
        // p -> scanChunkInternal(ctx, lookup, p)
        Stream<BlockPos> posStream = chunkPositions.parallelStream().flatMap(p -> scanChunkInternal(ctx, lookup, p));
        if (maxBlocks >= 0) {
            // WARNING: this can be expensive if maxBlocks is large...
            // see limit's javadoc
            posStream = posStream.limit(maxBlocks);
        }
        return posStream.collect(Collectors.toList());
    }

    private Stream<BlockPos> scanChunkInternal(IPlayerContext ctx, BlockOptionalMetaLookup lookup, ChunkPos pos) {
        AbstractChunkProvider chunkProvider = ctx.world().getChunkProvider();
        // if chunk is not loaded, return empty stream
        if (!chunkProvider.chunkExists(pos.x, pos.z)) {
            return Stream.empty();
        }

        long chunkX = (long) pos.x << 4;
        long chunkZ = (long) pos.z << 4;

        int playerSectionY = ctx.playerFeet().y >> 4;

        return collectChunkSections(lookup, chunkProvider.getChunk(pos.x, pos.z, false), chunkX, chunkZ, playerSectionY).stream();
    }


    private List<BlockPos> collectChunkSections(BlockOptionalMetaLookup lookup, Chunk chunk, long chunkX, long chunkZ, int playerSection) {
        // iterate over sections relative to player
        List<BlockPos> blocks = new ArrayList<>();
        ChunkSection[] sections = chunk.getSections();
        int l = sections.length;
        int i = playerSection - 1;
        int j = playerSection;
        for (; i >= 0 || j < l; ++j, --i) {
            if (j < l) {
                visitSection(lookup, sections[j], blocks, chunkX, chunkZ);
            }
            if (i >= 0) {
                visitSection(lookup, sections[i], blocks, chunkX, chunkZ);
            }
        }
        return blocks;
    }

    private void visitSection(BlockOptionalMetaLookup lookup, ChunkSection section, List<BlockPos> blocks, long chunkX, long chunkZ) {
        if (section == null || section.isEmpty()) {
            return;
        }

        PalettedContainer<BlockState> sectionContainer = section.getData();
        //this won't work if the PaletteStorage is of the type EmptyPaletteStorage
        ((IPalettedContainer<BlockState>) sectionContainer).getStorage();

        boolean[] isInFilter = getIncludedFilterIndices(lookup, ((IPalettedContainer<BlockState>) sectionContainer).getPalette());
        if (isInFilter.length == 0) {
            return;
        }

        BitArray array = ((IPalettedContainer<BlockState>) section.getData()).getStorage();
        long[] longArray = array.getBackingLongArray();
        int arraySize = array.size();
        int bitsPerEntry = ((IBitArray) array).getBitsPerEntry();
        long maxEntryValue = ((IBitArray) array).getMaxEntryValue();


        int yOffset = section.getYLocation();

        for (int i = 0, idx = 0; i < longArray.length && idx < arraySize; ++i) {
            long l = longArray[i];
            for (int offset = 0; offset <= (64 - bitsPerEntry) && idx < arraySize; offset += bitsPerEntry, ++idx) {
                int value = (int) ((l >> offset) & maxEntryValue);
                if (isInFilter[value]) {
                    //noinspection DuplicateExpressions
                    blocks.add(new BlockPos(
                            chunkX + ((idx & 255) & 15),
                            yOffset + (idx >> 8),
                            chunkZ + ((idx & 255) >> 4)
                    ));
                }
            }
        }
    }

    private boolean[] getIncludedFilterIndices(BlockOptionalMetaLookup lookup, IPalette<BlockState> palette) {
        boolean commonBlockFound = false;
        ObjectIntIdentityMap<BlockState> paletteMap = getPalette(palette);
        int size = paletteMap.size();

        boolean[] isInFilter = new boolean[size];

        for (int i = 0; i < size; i++) {
            BlockState state = paletteMap.getByValue(i);
            if (lookup.has(state)) {
                isInFilter[i] = true;
                commonBlockFound = true;
            } else {
                isInFilter[i] = false;
            }
        }

        if (!commonBlockFound) {
            return new boolean[0];
        }
        return isInFilter;
    }

    /**
     * cheats to get the actual map of id -> blockstate from the various palette implementations
     */
    private static ObjectIntIdentityMap<BlockState> getPalette(IPalette<BlockState> palette) {
        if (palette instanceof IdentityPalette) {
            return Block.BLOCK_STATE_IDS;
        } else {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            palette.write(buf);
            int size = buf.readVarInt();
            ObjectIntIdentityMap<BlockState> states = new ObjectIntIdentityMap<>();
            for (int i = 0; i < size; i++) {
                BlockState state = Block.BLOCK_STATE_IDS.getByValue(buf.readVarInt());
                if (state != null) {
                    states.put(state, i);
                }
            }
            return states;
        }
    }
}
