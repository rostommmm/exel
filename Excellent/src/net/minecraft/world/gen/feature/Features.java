package net.minecraft.world.gen.feature;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockplacer.ColumnBlockPlacer;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.*;
import net.minecraft.world.gen.foliageplacer.*;
import net.minecraft.world.gen.placement.*;
import net.minecraft.world.gen.treedecorator.*;
import net.minecraft.world.gen.trunkplacer.*;
import net.minecraft.world.server.ServerWorld;

import java.util.OptionalInt;
import java.util.function.Supplier;

public class Features
{
    public static final ConfiguredFeature <? , ? > END_SPIKE = register("end_spike", Feature.END_SPIKE.withConfiguration(new EndSpikeFeatureConfig(false, ImmutableList.of(), (BlockPos)null)));
    public static final ConfiguredFeature <? , ? > END_GATEWAY = register("end_gateway", Feature.END_GATEWAY.withConfiguration(EndGatewayConfig.func_214702_a(ServerWorld.field_241108_a_, true)).withPlacement(Placement.END_GATEWAY.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature <? , ? > END_GATEWAY_DELAYED = register("end_gateway_delayed", Feature.END_GATEWAY.withConfiguration(EndGatewayConfig.func_214698_a()));
    public static final ConfiguredFeature <? , ? > CHORUS_PLANT = register("chorus_plant", Feature.CHORUS_PLANT.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(4));
    public static final ConfiguredFeature <? , ? > END_ISLAND = register("end_island", Feature.END_ISLAND.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature <? , ? > END_ISLAND_DECORATED = register("end_island_decorated", END_ISLAND.withPlacement(Placement.END_ISLAND.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature <? , ? > DELTA = register("delta", Feature.DELTA_FEATURE.withConfiguration(new BasaltDeltasFeature(Features.States.LAVA_BLOCK, Features.States.MAGMA_BLOCK, FeatureSpread.func_242253_a(3, 4), FeatureSpread.func_242253_a(0, 2))).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(40))));
    public static final ConfiguredFeature <? , ? > SMALL_BASALT_COLUMNS = register("small_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(1, 3))).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(4))));
    public static final ConfiguredFeature <? , ? > LARGE_BASALT_COLUMNS = register("large_basalt_columns", Feature.BASALT_COLUMNS.withConfiguration(new ColumnConfig(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(5, 5))).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(2))));
    public static final ConfiguredFeature <? , ? > BASALT_BLOBS = register("basalt_blobs", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(Features.States.NETHERRACK, Features.States.BASALT, FeatureSpread.func_242253_a(3, 4))).func_242733_d(128).func_242728_a().func_242731_b(75));
    public static final ConfiguredFeature <? , ? > BLACKSTONE_BLOBS = register("blackstone_blobs", Feature.NETHERRACK_REPLACE_BLOBS.withConfiguration(new BlobReplacementConfig(Features.States.NETHERRACK, Features.States.BLACKSTONE, FeatureSpread.func_242253_a(3, 4))).func_242733_d(128).func_242728_a().func_242731_b(25));
    public static final ConfiguredFeature <? , ? > GLOWSTONE_EXTRA = register("glowstone_extra", Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.field_242912_w.configure(new FeatureSpreadConfig(10))));
    public static final ConfiguredFeature <? , ? > GLOWSTONE = register("glowstone", Feature.GLOWSTONE_BLOB.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > CRIMSON_FOREST_VEGETATION = register("crimson_forest_vegetation", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Features.Configs.CRIMSON_FOREST_VEGETATION_CONFIG).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(6))));
    public static final ConfiguredFeature <? , ? > WARPED_FOREST_VEGETATION = register("warped_forest_vegetation", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Features.Configs.WARPED_FOREST_VEGETATION_CONFIG).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(5))));
    public static final ConfiguredFeature <? , ? > NETHER_SPROUTS = register("nether_sprouts", Feature.NETHER_FOREST_VEGETATION.withConfiguration(Features.Configs.NETHER_SPROUTS_CONFIG).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(4))));
    public static final ConfiguredFeature <? , ? > TWISTING_VINES = register("twisting_vines", Feature.TWISTING_VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > WEEPING_VINES = register("weeping_vines", Feature.WEEPING_VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > BASALT_PILLAR = register("basalt_pillar", Feature.BASALT_PILLAR.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(128).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > SEAGRASS_COLD = register("seagrass_cold", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3F)).func_242731_b(32).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_DEEP_COLD = register("seagrass_deep_cold", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8F)).func_242731_b(40).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_NORMAL = register("seagrass_normal", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3F)).func_242731_b(48).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_RIVER = register("seagrass_river", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.4F)).func_242731_b(48).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_DEEP = register("seagrass_deep", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8F)).func_242731_b(48).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_SWAMP = register("seagrass_swamp", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.6F)).func_242731_b(64).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_WARM = register("seagrass_warm", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.3F)).func_242731_b(80).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEAGRASS_DEEP_WARM = register("seagrass_deep_warm", Feature.SEAGRASS.withConfiguration(new ProbabilityConfig(0.8F)).func_242731_b(80).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > SEA_PICKLE = register("sea_pickle", Feature.SEA_PICKLE.withConfiguration(new FeatureSpreadConfig(20)).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).func_242729_a(16));
    public static final ConfiguredFeature <? , ? > ICE_SPIKE = register("ice_spike", Feature.ICE_SPIKE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(3));
    public static final ConfiguredFeature <? , ? > ICE_PATCH = register("ice_patch", Feature.ICE_PATCH.withConfiguration(new SphereReplaceConfig(Features.States.PACKED_ICE, FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(Features.States.DIRT, Features.States.GRASS_BLOCK, Features.States.PODZOL, Features.States.COARSE_DIRT, Features.States.MYCELIUM, Features.States.SNOW_BLOCK, Features.States.ICE))).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature <? , ? > FOREST_ROCK = register("forest_rock", Feature.FOREST_ROCK.withConfiguration(new BlockStateFeatureConfig(Features.States.MOSSY_COBBLESTONE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242732_c(2));
    public static final ConfiguredFeature <? , ? > SEAGRASS_SIMPLE = register("seagrass_simple", Feature.SIMPLE_BLOCK.withConfiguration(new BlockWithContextConfig(Features.States.SEAGRASS, ImmutableList.of(Features.States.STONE), ImmutableList.of(Features.States.WATER_BLOCK), ImmutableList.of(Features.States.WATER_BLOCK))).withPlacement(Placement.CARVING_MASK.configure(new CaveEdgeConfig(GenerationStage.Carving.LIQUID, 0.1F))));
    public static final ConfiguredFeature <? , ? > ICEBERG_PACKED = register("iceberg_packed", Feature.ICEBERG.withConfiguration(new BlockStateFeatureConfig(Features.States.PACKED_ICE)).withPlacement(Placement.ICEBERG.configure(NoPlacementConfig.field_236556_b_)).func_242729_a(16));
    public static final ConfiguredFeature <? , ? > ICEBERG_BLUE = register("iceberg_blue", Feature.ICEBERG.withConfiguration(new BlockStateFeatureConfig(Features.States.BLUE_ICE)).withPlacement(Placement.ICEBERG.configure(NoPlacementConfig.field_236556_b_)).func_242729_a(200));
    public static final ConfiguredFeature <? , ? > KELP_COLD = register("kelp_cold", Feature.KELP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.KELP_PLACEMENT).func_242728_a().withPlacement(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(120, 80.0D, 0.0D))));
    public static final ConfiguredFeature <? , ? > KELP_WARM = register("kelp_warm", Feature.KELP.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.KELP_PLACEMENT).func_242728_a().withPlacement(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(80, 80.0D, 0.0D))));
    public static final ConfiguredFeature <? , ? > BLUE_ICE = register("blue_ice", Feature.BLUE_ICE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(30, 32, 64))).func_242728_a().func_242732_c(19));
    public static final ConfiguredFeature <? , ? > BAMBOO_LIGHT = register("bamboo_light", Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.0F)).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(16));
    public static final ConfiguredFeature <? , ? > BAMBOO = register("bamboo", Feature.BAMBOO.withConfiguration(new ProbabilityConfig(0.2F)).withPlacement(Features.Placements.BAMBOO_PLACEMENT).func_242728_a().withPlacement(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(160, 80.0D, 0.3D))));
    public static final ConfiguredFeature <? , ? > VINES = register("vines", Feature.VINES.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242728_a().func_242731_b(50));
    public static final ConfiguredFeature <? , ? > LAKE_WATER = register("lake_water", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Features.States.WATER_BLOCK)).withPlacement(Placement.WATER_LAKE.configure(new ChanceConfig(4))));
    public static final ConfiguredFeature <? , ? > LAKE_LAVA = register("lake_lava", Feature.LAKE.withConfiguration(new BlockStateFeatureConfig(Features.States.LAVA_BLOCK)).withPlacement(Placement.LAVA_LAKE.configure(new ChanceConfig(80))));
    public static final ConfiguredFeature <? , ? > DISK_CLAY = register("disk_clay", Feature.DISK.withConfiguration(new SphereReplaceConfig(Features.States.CLAY, FeatureSpread.func_242253_a(2, 1), 1, ImmutableList.of(Features.States.DIRT, Features.States.CLAY))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > DISK_GRAVEL = register("disk_gravel", Feature.DISK.withConfiguration(new SphereReplaceConfig(Features.States.GRAVEL, FeatureSpread.func_242253_a(2, 3), 2, ImmutableList.of(Features.States.DIRT, Features.States.GRASS_BLOCK))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT));
    public static final ConfiguredFeature <? , ? > DISK_SAND = register("disk_sand", Feature.DISK.withConfiguration(new SphereReplaceConfig(Features.States.SAND, FeatureSpread.func_242253_a(2, 4), 2, ImmutableList.of(Features.States.DIRT, Features.States.GRASS_BLOCK))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).func_242731_b(3));
    public static final ConfiguredFeature <? , ? > FREEZE_TOP_LAYER = register("freeze_top_layer", Feature.FREEZE_TOP_LAYER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature <? , ? > BONUS_CHEST = register("bonus_chest", Feature.BONUS_CHEST.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature <? , ? > VOID_START_PLATFORM = register("void_start_platform", Feature.VOID_START_PLATFORM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
    public static final ConfiguredFeature <? , ? > MONSTER_ROOM = register("monster_room", Feature.MONSTER_ROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242733_d(256).func_242728_a().func_242731_b(8));
    public static final ConfiguredFeature <? , ? > DESERT_WELL = register("desert_well", Feature.DESERT_WELL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242729_a(1000));
    public static final ConfiguredFeature <? , ? > FOSSIL = register("fossil", Feature.FOSSIL.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).func_242729_a(64));
    public static final ConfiguredFeature <? , ? > SPRING_LAVA_DOUBLE = register("spring_lava_double", Feature.SPRING_FEATURE.withConfiguration(Features.Configs.LAVA_SPRING_CONFIG).withPlacement(Placement.field_242909_n.configure(new TopSolidRangeConfig(8, 16, 256))).func_242728_a().func_242731_b(40));
    public static final ConfiguredFeature <? , ? > SPRING_LAVA = register("spring_lava", Feature.SPRING_FEATURE.withConfiguration(Features.Configs.LAVA_SPRING_CONFIG).withPlacement(Placement.field_242909_n.configure(new TopSolidRangeConfig(8, 16, 256))).func_242728_a().func_242731_b(20));
    public static final ConfiguredFeature <? , ? > SPRING_DELTA = register("spring_delta", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Features.States.LAVA, true, 4, 1, ImmutableSet.of(Blocks.NETHERRACK, Blocks.SOUL_SAND, Blocks.GRAVEL, Blocks.MAGMA_BLOCK, Blocks.BLACKSTONE))).withPlacement(Features.Placements.SPRING_PLACEMENT).func_242728_a().func_242731_b(16));
    public static final ConfiguredFeature <? , ? > SPRING_CLOSED = register("spring_closed", Feature.SPRING_FEATURE.withConfiguration(Features.Configs.CLOSED_SPRING_CONFIG).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(16));
    public static final ConfiguredFeature <? , ? > SPRING_CLOSED_DOUBLE = register("spring_closed_double", Feature.SPRING_FEATURE.withConfiguration(Features.Configs.CLOSED_SPRING_CONFIG).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(32));
    public static final ConfiguredFeature <? , ? > SPRING_OPEN = register("spring_open", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Features.States.LAVA, false, 4, 1, ImmutableSet.of(Blocks.NETHERRACK))).withPlacement(Features.Placements.SPRING_PLACEMENT).func_242728_a().func_242731_b(8));
    public static final ConfiguredFeature <? , ? > SPRING_WATER = register("spring_water", Feature.SPRING_FEATURE.withConfiguration(new LiquidsConfig(Features.States.WATER, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE))).withPlacement(Placement.field_242908_m.configure(new TopSolidRangeConfig(8, 8, 256))).func_242728_a().func_242731_b(50));
    public static final ConfiguredFeature <? , ? > PILE_HAY = register("pile_hay", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new AxisRotatingBlockStateProvider(Blocks.HAY_BLOCK))));
    public static final ConfiguredFeature <? , ? > PILE_MELON = register("pile_melon", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Features.States.MELON))));
    public static final ConfiguredFeature <? , ? > PILE_SNOW = register("pile_snow", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Features.States.SNOW))));
    public static final ConfiguredFeature <? , ? > PILE_ICE = register("pile_ice", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.BLUE_ICE, 1).addWeightedBlockstate(Features.States.PACKED_ICE, 5))));
    public static final ConfiguredFeature <? , ? > PILE_PUMPKIN = register("pile_pumpkin", Feature.BLOCK_PILE.withConfiguration(new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.PUMPKIN, 19).addWeightedBlockstate(Features.States.JACK_O_LANTERN, 1))));
    public static final ConfiguredFeature <? , ? > PATCH_FIRE = register("patch_fire", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.FIRE), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.NETHERRACK.getBlock())).func_227317_b_().build()).withPlacement(Features.Placements.FIRE_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_SOUL_FIRE = register("patch_soul_fire", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SOUL_FIRE), new SimpleBlockPlacer())).tries(64).whitelist(ImmutableSet.of(Features.States.SOUL_SOIL.getBlock())).func_227317_b_().build()).withPlacement(Features.Placements.FIRE_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_BROWN_MUSHROOM = register("patch_brown_mushroom", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.BROWN_MUSHROOM), SimpleBlockPlacer.PLACER)).tries(64).func_227317_b_().build()));
    public static final ConfiguredFeature <? , ? > PATCH_RED_MUSHROOM = register("patch_red_mushroom", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.RED_MUSHROOM), SimpleBlockPlacer.PLACER)).tries(64).func_227317_b_().build()));
    public static final ConfiguredFeature <? , ? > PATCH_CRIMSON_ROOTS = register("patch_crimson_roots", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.CRIMSON_ROOTS), new SimpleBlockPlacer())).tries(64).func_227317_b_().build()).func_242733_d(128));
    public static final ConfiguredFeature <? , ? > PATCH_SUNFLOWER = register("patch_sunflower", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SUNFLOWER), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature <? , ? > PATCH_PUMPKIN = register("patch_pumpkin", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.PUMPKIN), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.GRASS_BLOCK.getBlock())).func_227317_b_().build()).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(32));
    public static final ConfiguredFeature <? , ? > PATCH_TAIGA_GRASS = register("patch_taiga_grass", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TAIGA_GRASS_CONFIG));
    public static final ConfiguredFeature <? , ? > PATCH_BERRY_BUSH = register("patch_berry_bush", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.BERRY_BUSH_PATCH_CONFIG));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_PLAIN = register("patch_grass_plain", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).withPlacement(Placement.field_242900_d.configure(new NoiseDependant(-0.8D, 5, 10))));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_FOREST = register("patch_grass_forest", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_BADLANDS = register("patch_grass_badlands", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_SAVANNA = register("patch_grass_savanna", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_NORMAL = register("patch_grass_normal", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.GRASS_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_TAIGA_2 = register("patch_grass_taiga_2", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TAIGA_GRASS_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_TAIGA = register("patch_grass_taiga", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TAIGA_GRASS_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature <? , ? > PATCH_GRASS_JUNGLE = register("patch_grass_jungle", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.JUNGLE_VEGETATION_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(25));
    public static final ConfiguredFeature <? , ? > PATCH_DEAD_BUSH_2 = register("patch_dead_bush_2", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.DEAD_BUSH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature <? , ? > PATCH_DEAD_BUSH = register("patch_dead_bush", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.DEAD_BUSH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_DEAD_BUSH_BADLANDS = register("patch_dead_bush_badlands", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.DEAD_BUSH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature <? , ? > PATCH_MELON = register("patch_melon", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.MELON), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.GRASS_BLOCK.getBlock())).replaceable().func_227317_b_().build()).withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_BERRY_SPARSE = register("patch_berry_sparse", PATCH_BERRY_BUSH.withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > PATCH_BERRY_DECORATED = register("patch_berry_decorated", PATCH_BERRY_BUSH.withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(12));
    public static final ConfiguredFeature <? , ? > PATCH_WATERLILLY = register("patch_waterlilly", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.LILY_PAD), SimpleBlockPlacer.PLACER)).tries(10).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(4));
    public static final ConfiguredFeature <? , ? > PATCH_TALL_GRASS_2 = register("patch_tall_grass_2", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).func_242728_a().withPlacement(Placement.field_242900_d.configure(new NoiseDependant(-0.8D, 0, 7))));
    public static final ConfiguredFeature <? , ? > PATCH_TALL_GRASS = register("patch_tall_grass", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.TALL_GRASS_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature <? , ? > PATCH_LARGE_FERN = register("patch_large_fern", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.LARGE_FERN), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(7));
    public static final ConfiguredFeature <? , ? > PATCH_CACTUS = register("patch_cactus", Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.CACTUS), new ColumnBlockPlacer(1, 2))).tries(10).func_227317_b_().build()));
    public static final ConfiguredFeature <? , ? > PATCH_CACTUS_DESERT = register("patch_cactus_desert", PATCH_CACTUS.withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature <? , ? > PATCH_CACTUS_DECORATED = register("patch_cactus_decorated", PATCH_CACTUS.withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature <? , ? > PATCH_SUGAR_CANE_SWAMP = register("patch_sugar_cane_swamp", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(20));
    public static final ConfiguredFeature <? , ? > PATCH_SUGAR_CANE_DESERT = register("patch_sugar_cane_desert", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(60));
    public static final ConfiguredFeature <? , ? > PATCH_SUGAR_CANE_BADLANDS = register("patch_sugar_cane_badlands", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(13));
    public static final ConfiguredFeature <? , ? > PATCH_SUGAR_CANE = register("patch_sugar_cane", Feature.RANDOM_PATCH.withConfiguration(Features.Configs.SUGAR_CANE_PATCH_CONFIG).withPlacement(Features.Placements.PATCH_PLACEMENT).func_242731_b(10));
    public static final ConfiguredFeature <? , ? > BROWN_MUSHROOM_NETHER = register("brown_mushroom_nether", PATCH_BROWN_MUSHROOM.func_242733_d(128).func_242729_a(2));
    public static final ConfiguredFeature <? , ? > RED_MUSHROOM_NETHER = register("red_mushroom_nether", PATCH_RED_MUSHROOM.func_242733_d(128).func_242729_a(2));
    public static final ConfiguredFeature <? , ? > BROWN_MUSHROOM_NORMAL = register("brown_mushroom_normal", PATCH_BROWN_MUSHROOM.withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(4));
    public static final ConfiguredFeature <? , ? > RED_MUSHROOM_NORMAL = register("red_mushroom_normal", PATCH_RED_MUSHROOM.withPlacement(Features.Placements.PATCH_PLACEMENT).func_242729_a(8));
    public static final ConfiguredFeature <? , ? > BROWN_MUSHROOM_TAIGA = register("brown_mushroom_taiga", PATCH_BROWN_MUSHROOM.func_242729_a(4).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));
    public static final ConfiguredFeature <? , ? > RED_MUSHROOM_TAIGA = register("red_mushroom_taiga", PATCH_RED_MUSHROOM.func_242729_a(8).withPlacement(Features.Placements.PATCH_PLACEMENT));
    public static final ConfiguredFeature <? , ? > BROWN_MUSHROOM_GIANT = register("brown_mushroom_giant", BROWN_MUSHROOM_TAIGA.func_242731_b(3));
    public static final ConfiguredFeature <? , ? > RED_MUSHROOM_GIANT = register("red_mushroom_giant", RED_MUSHROOM_TAIGA.func_242731_b(3));
    public static final ConfiguredFeature <? , ? > BROWN_MUSHROOM_SWAMP = register("brown_mushroom_swamp", BROWN_MUSHROOM_TAIGA.func_242731_b(8));
    public static final ConfiguredFeature <? , ? > RED_MUSHROOM_SWAMP = register("red_mushroom_swamp", RED_MUSHROOM_TAIGA.func_242731_b(8));
    public static final ConfiguredFeature <? , ? > ORE_MAGMA = register("ore_magma", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.MAGMA_BLOCK, 33)).withPlacement(Placement.MAGMA.configure(NoPlacementConfig.field_236556_b_)).func_242728_a().func_242731_b(4));
    public static final ConfiguredFeature <? , ? > ORE_SOUL_SAND = register("ore_soul_sand", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.SOUL_SAND, 12)).func_242733_d(32).func_242728_a().func_242731_b(12));
    public static final ConfiguredFeature <? , ? > ORE_GOLD_DELTAS = register("ore_gold_deltas", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.NETHER_GOLD_ORE, 10)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(20));
    public static final ConfiguredFeature <? , ? > ORE_QUARTZ_DELTAS = register("ore_quartz_deltas", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.NETHER_QUARTZ_ORE, 14)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(32));
    public static final ConfiguredFeature <? , ? > ORE_GOLD_NETHER = register("ore_gold_nether", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.NETHER_GOLD_ORE, 10)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > ORE_QUARTZ_NETHER = register("ore_quartz_nether", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.NETHER_QUARTZ_ORE, 14)).withPlacement(Features.Placements.NETHER_SPRING_ORE_PLACEMENT).func_242728_a().func_242731_b(16));
    public static final ConfiguredFeature <? , ? > ORE_GRAVEL_NETHER = register("ore_gravel_nether", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.GRAVEL, 33)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(5, 0, 37))).func_242728_a().func_242731_b(2));
    public static final ConfiguredFeature <? , ? > ORE_BLACKSTONE = register("ore_blackstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241883_b, Features.States.BLACKSTONE, 33)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(5, 10, 37))).func_242728_a().func_242731_b(2));
    public static final ConfiguredFeature <? , ? > ORE_DIRT = register("ore_dirt", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.DIRT, 33)).func_242733_d(256).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > ORE_GRAVEL = register("ore_gravel", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.GRAVEL, 33)).func_242733_d(256).func_242728_a().func_242731_b(8));
    public static final ConfiguredFeature <? , ? > ORE_GRANITE = register("ore_granite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.GRANITE, 33)).func_242733_d(80).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > ORE_DIORITE = register("ore_diorite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.DIORITE, 33)).func_242733_d(80).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > ORE_ANDESITE = register("ore_andesite", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.ANDESITE, 33)).func_242733_d(80).func_242728_a().func_242731_b(10));
    public static final ConfiguredFeature <? , ? > ORE_COAL = register("ore_coal", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.COAL_ORE, 17)).func_242733_d(128).func_242728_a().func_242731_b(20));
    public static final ConfiguredFeature <? , ? > ORE_IRON = register("ore_iron", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.IRON_ORE, 9)).func_242733_d(64).func_242728_a().func_242731_b(20));
    public static final ConfiguredFeature <? , ? > ORE_GOLD_EXTRA = register("ore_gold_extra", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.GOLD_ORE, 9)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(32, 32, 80))).func_242728_a().func_242731_b(20));
    public static final ConfiguredFeature <? , ? > ORE_GOLD = register("ore_gold", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.GOLD_ORE, 9)).func_242733_d(32).func_242728_a().func_242731_b(2));
    public static final ConfiguredFeature <? , ? > ORE_REDSTONE = register("ore_redstone", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.REDSTONE_ORE, 8)).func_242733_d(16).func_242728_a().func_242731_b(8));
    public static final ConfiguredFeature <? , ? > ORE_DIAMOND = register("ore_diamond", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.DIAMOND_ORE, 8)).func_242733_d(16).func_242728_a());
    public static final ConfiguredFeature <? , ? > ORE_LAPIS = register("ore_lapis", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.LAPIS_ORE, 7)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(16, 16))).func_242728_a());
    public static final ConfiguredFeature <? , ? > ORE_INFESTED = register("ore_infested", Feature.ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241882_a, Features.States.INFESTED_STONE, 9)).func_242733_d(64).func_242728_a().func_242731_b(7));
    public static final ConfiguredFeature <? , ? > ORE_EMERALD = register("ore_emerald", Feature.EMERALD_ORE.withConfiguration(new ReplaceBlockConfig(Features.States.STONE, Features.States.EMERALD_ORE)).withPlacement(Placement.EMERALD_ORE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature <? , ? > ORE_DEBRIS_LARGE = register("ore_debris_large", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241884_c, Features.States.ANCIENT_DEBRIS, 3)).withPlacement(Placement.field_242910_o.configure(new DepthAverageConfig(16, 8))).func_242728_a());
    public static final ConfiguredFeature <? , ? > ORE_DEBRIS_SMALL = register("ore_debris_small", Feature.NO_SURFACE_ORE.withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.field_241884_c, Features.States.ANCIENT_DEBRIS, 2)).withPlacement(Placement.field_242907_l.configure(new TopSolidRangeConfig(8, 16, 128))).func_242728_a());
    public static final ConfiguredFeature <? , ? > CRIMSON_FUNGI = register("crimson_fungi", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236300_c_).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(8))));
    public static final ConfiguredFeature < HugeFungusConfig, ? > CRIMSON_FUNGI_PLANTED = register("crimson_fungi_planted", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236299_b_));
    public static final ConfiguredFeature <? , ? > WARPED_FUNGI = register("warped_fungi", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236302_e_).withPlacement(Placement.field_242897_C.configure(new FeatureSpreadConfig(8))));
    public static final ConfiguredFeature < HugeFungusConfig, ? > WARPED_FUNGI_PLANTED = register("warped_fungi_planted", Feature.HUGE_FUNGUS.withConfiguration(HugeFungusConfig.field_236301_d_));
    public static final ConfiguredFeature <? , ? > HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom", Feature.HUGE_BROWN_MUSHROOM.withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(Features.States.BROWN_MUSHROOM_BLOCK_UP), new SimpleBlockStateProvider(Features.States.MUSHROOM_STEM), 3)));
    public static final ConfiguredFeature <? , ? > HUGE_RED_MUSHROOM = register("huge_red_mushroom", Feature.HUGE_RED_MUSHROOM.withConfiguration(new BigMushroomFeatureConfig(new SimpleBlockStateProvider(Features.States.RED_MUSHROOM_BLOCK_DOWN), new SimpleBlockStateProvider(Features.States.MUSHROOM_STEM), 2)));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > OAK = register("oak", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.OAK_LOG), new SimpleBlockStateProvider(Features.States.OAK_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > DARK_OAK = register("dark_oak", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.DARK_OAK_LOG), new SimpleBlockStateProvider(Features.States.DARK_OAK_LEAVES), new DarkOakFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0)), new DarkOakTrunkPlacer(6, 2, 1), new ThreeLayerFeature(1, 1, 0, 1, 2, OptionalInt.empty()))).func_236701_a_(Integer.MAX_VALUE).func_236702_a_(Heightmap.Type.MOTION_BLOCKING).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > BIRCH = register("birch", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.BIRCH_LOG), new SimpleBlockStateProvider(Features.States.BIRCH_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > ACACIA = register("acacia", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.ACACIA_LOG), new SimpleBlockStateProvider(Features.States.ACACIA_LEAVES), new AcaciaFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0)), new ForkyTrunkPlacer(5, 2, 2), new TwoLayerFeature(1, 0, 2))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > SPRUCE = register("spruce", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SPRUCE_LOG), new SimpleBlockStateProvider(Features.States.SPRUCE_LEAVES), new SpruceFoliagePlacer(FeatureSpread.func_242253_a(2, 1), FeatureSpread.func_242253_a(0, 2), FeatureSpread.func_242253_a(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > PINE = register("pine", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SPRUCE_LOG), new SimpleBlockStateProvider(Features.States.SPRUCE_LEAVES), new PineFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), FeatureSpread.func_242253_a(3, 1)), new StraightTrunkPlacer(6, 4, 0), new TwoLayerFeature(2, 0, 2))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > JUNGLE_TREE = register("jungle_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.JUNGLE_LOG), new SimpleBlockStateProvider(Features.States.JUNGLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1))).func_236703_a_(ImmutableList.of(new CocoaTreeDecorator(0.2F), TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > FANCY_OAK = register("fancy_oak", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.OAK_LOG), new SimpleBlockStateProvider(Features.States.OAK_LEAVES), new FancyFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(4), 4), new FancyTrunkPlacer(3, 11, 0), new TwoLayerFeature(0, 0, 0, OptionalInt.of(4)))).setIgnoreVines().func_236702_a_(Heightmap.Type.MOTION_BLOCKING).build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > JUNGLE_TREE_NO_VINE = register("jungle_tree_no_vine", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.JUNGLE_LOG), new SimpleBlockStateProvider(Features.States.JUNGLE_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(4, 8, 0), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > MEGA_JUNGLE_TREE = register("mega_jungle_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.JUNGLE_LOG), new SimpleBlockStateProvider(Features.States.JUNGLE_LEAVES), new JungleFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 2), new MegaJungleTrunkPlacer(10, 2, 19), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(TrunkVineTreeDecorator.field_236879_b_, LeaveVineTreeDecorator.field_236871_b_)).build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > MEGA_SPRUCE = register("mega_spruce", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SPRUCE_LOG), new SimpleBlockStateProvider(Features.States.SPRUCE_LEAVES), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(13, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Features.States.PODZOL)))).build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > MEGA_PINE = register("mega_pine", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SPRUCE_LOG), new SimpleBlockStateProvider(Features.States.SPRUCE_LEAVES), new MegaPineFoliagePlacer(FeatureSpread.func_242252_a(0), FeatureSpread.func_242252_a(0), FeatureSpread.func_242253_a(3, 4)), new GiantTrunkPlacer(13, 2, 14), new TwoLayerFeature(1, 1, 2))).func_236703_a_(ImmutableList.of(new AlterGroundTreeDecorator(new SimpleBlockStateProvider(Features.States.PODZOL)))).build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > SUPER_BIRCH_BEES_0002 = register("super_birch_bees_0002", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.BIRCH_LOG), new SimpleBlockStateProvider(Features.States.BIRCH_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 2, 6), new TwoLayerFeature(1, 0, 1))).setIgnoreVines().func_236703_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT)).build()));
    public static final ConfiguredFeature <? , ? > SWAMP_TREE = register("swamp_tree", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.OAK_LOG), new SimpleBlockStateProvider(Features.States.OAK_LEAVES), new BlobFoliagePlacer(FeatureSpread.func_242252_a(3), FeatureSpread.func_242252_a(0), 3), new StraightTrunkPlacer(5, 3, 0), new TwoLayerFeature(1, 0, 1))).func_236701_a_(1).func_236703_a_(ImmutableList.of(LeaveVineTreeDecorator.field_236871_b_)).build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > JUNGLE_BUSH = register("jungle_bush", Feature.TREE.withConfiguration((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.JUNGLE_LOG), new SimpleBlockStateProvider(Features.States.OAK_LEAVES), new BushFoliagePlacer(FeatureSpread.func_242252_a(2), FeatureSpread.func_242252_a(1), 2), new StraightTrunkPlacer(1, 0, 0), new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > OAK_BEES_0002 = register("oak_bees_0002", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > OAK_BEES_002 = register("oak_bees_002", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > OAK_BEES_005 = register("oak_bees_005", Feature.TREE.withConfiguration(OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > BIRCH_BEES_0002 = register("birch_bees_0002", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > BIRCH_BEES_002 = register("birch_bees_002", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > BIRCH_BEES_005 = register("birch_bees_005", Feature.TREE.withConfiguration(BIRCH.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > FANCY_OAK_BEES_0002 = register("fancy_oak_bees_0002", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_0002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > FANCY_OAK_BEES_002 = register("fancy_oak_bees_002", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_002_PLACEMENT))));
    public static final ConfiguredFeature < BaseTreeFeatureConfig, ? > FANCY_OAK_BEES_005 = register("fancy_oak_bees_005", Feature.TREE.withConfiguration(FANCY_OAK.func_242767_c().func_236685_a_(ImmutableList.of(Features.Placements.BEES_005_PLACEMENT))));
    public static final ConfiguredFeature <? , ? > OAK_BADLANDS = register("oak_badlands", OAK.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(5, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > SPRUCE_SNOWY = register("spruce_snowy", SPRUCE.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > FLOWER_WARM = register("flower_warm", Feature.FLOWER.withConfiguration(Features.Configs.NORMAL_FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(4));
    public static final ConfiguredFeature <? , ? > FLOWER_DEFAULT = register("flower_default", Feature.FLOWER.withConfiguration(Features.Configs.NORMAL_FLOWER_CONFIG).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(2));
    public static final ConfiguredFeature <? , ? > FLOWER_FOREST = register("flower_forest", Feature.FLOWER.withConfiguration((new BlockClusterFeatureConfig.Builder(ForestFlowerBlockStateProvider.PROVIDER, SimpleBlockPlacer.PLACER)).tries(64).build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(100));
    public static final ConfiguredFeature <? , ? > FLOWER_SWAMP = register("flower_swamp", Feature.FLOWER.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.BLUE_ORCHID), SimpleBlockPlacer.PLACER)).tries(64).build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));
    public static final ConfiguredFeature <? , ? > FLOWER_PLAIN = register("flower_plain", Feature.FLOWER.withConfiguration((new BlockClusterFeatureConfig.Builder(PlainFlowerBlockStateProvider.PROVIDER, SimpleBlockPlacer.PLACER)).tries(64).build()));
    public static final ConfiguredFeature <? , ? > FLOWER_PLAIN_DECORATED = register("flower_plain_decorated", FLOWER_PLAIN.withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.FLOWER_TALL_GRASS_PLACEMENT).func_242728_a().withPlacement(Placement.field_242900_d.configure(new NoiseDependant(-0.8D, 15, 4))));
    private static final ImmutableList < Supplier < ConfiguredFeature <? , ? >>> FOREST_FLOWER_VEGETATION_LIST = ImmutableList.of(() ->
    {
        return Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.LILAC), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build());
    }, () ->
    {
        return Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.ROSE_BUSH), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build());
    }, () ->
    {
        return Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.PEONY), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build());
    }, () ->
    {
        return Feature.NO_BONEMEAL_FLOWER.withConfiguration((new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.LILY_OF_THE_VALLEY), SimpleBlockPlacer.PLACER)).tries(64).build());
    });
    public static final ConfiguredFeature <? , ? > FOREST_FLOWER_VEGETATION_COMMON = register("forest_flower_vegetation_common", Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(FOREST_FLOWER_VEGETATION_LIST)).func_242730_a(FeatureSpread.func_242253_a(-1, 4)).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature <? , ? > FOREST_FLOWER_VEGETATION = register("forest_flower_vegetation", Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(FOREST_FLOWER_VEGETATION_LIST)).func_242730_a(FeatureSpread.func_242253_a(-3, 4)).withPlacement(Features.Placements.VEGETATION_PLACEMENT).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).func_242731_b(5));
    public static final ConfiguredFeature <? , ? > DARK_FOREST_VEGETATION_BROWN = register("dark_forest_vegetation_brown", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(HUGE_BROWN_MUSHROOM.withChance(0.025F), HUGE_RED_MUSHROOM.withChance(0.05F), DARK_OAK.withChance(0.6666667F), BIRCH.withChance(0.2F), FANCY_OAK.withChance(0.1F)), OAK)).withPlacement(Placement.DARK_OAK_TREE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature <? , ? > DARK_FOREST_VEGETATION_RED = register("dark_forest_vegetation_red", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(HUGE_RED_MUSHROOM.withChance(0.025F), HUGE_BROWN_MUSHROOM.withChance(0.05F), DARK_OAK.withChance(0.6666667F), BIRCH.withChance(0.2F), FANCY_OAK.withChance(0.1F)), OAK)).withPlacement(Placement.DARK_OAK_TREE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    public static final ConfiguredFeature <? , ? > WARM_OCEAN_VEGETATION = register("warm_ocean_vegetation", Feature.SIMPLE_RANDOM_SELECTOR.withConfiguration(new SingleRandomFeature(ImmutableList.of(() ->
    {
        return Feature.CORAL_TREE.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }, () ->
    {
        return Feature.CORAL_CLAW.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }, () ->
    {
        return Feature.CORAL_MUSHROOM.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    }))).withPlacement(Features.Placements.KELP_PLACEMENT).func_242728_a().withPlacement(Placement.field_242901_e.configure(new TopSolidWithNoiseConfig(20, 400.0D, 0.0D))));
    public static final ConfiguredFeature <? , ? > FOREST_FLOWER_TREES = register("forest_flower_trees", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BIRCH_BEES_002.withChance(0.2F), FANCY_OAK_BEES_002.withChance(0.1F)), OAK_BEES_002)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(6, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TAIGA_VEGETATION = register("taiga_vegetation", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(PINE.withChance(0.33333334F)), SPRUCE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_SHATTERED_SAVANNA = register("trees_shattered_savanna", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(ACACIA.withChance(0.8F)), OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_SAVANNA = register("trees_savanna", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(ACACIA.withChance(0.8F)), OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(1, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > BIRCH_TALL = register("birch_tall", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SUPER_BIRCH_BEES_0002.withChance(0.5F)), BIRCH_BEES_0002)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_BIRCH = register("trees_birch", BIRCH_BEES_0002.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_MOUNTAIN_EDGE = register("trees_mountain_edge", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SPRUCE.withChance(0.666F), FANCY_OAK.withChance(0.1F)), OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(3, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_MOUNTAIN = register("trees_mountain", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(SPRUCE.withChance(0.666F), FANCY_OAK.withChance(0.1F)), OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_WATER = register("trees_water", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1F)), OAK)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > BIRCH_OTHER = register("birch_other", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(BIRCH_BEES_0002.withChance(0.2F), FANCY_OAK_BEES_0002.withChance(0.1F)), OAK_BEES_0002)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > PLAIN_VEGETATION = register("plain_vegetation", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK_BEES_005.withChance(0.33333334F)), OAK_BEES_005)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(0, 0.05F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_JUNGLE_EDGE = register("trees_jungle_edge", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1F), JUNGLE_BUSH.withChance(0.5F)), JUNGLE_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(2, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_GIANT_SPRUCE = register("trees_giant_spruce", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(MEGA_SPRUCE.withChance(0.33333334F), PINE.withChance(0.33333334F)), SPRUCE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_GIANT = register("trees_giant", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(MEGA_SPRUCE.withChance(0.025641026F), MEGA_PINE.withChance(0.30769232F), PINE.withChance(0.33333334F)), SPRUCE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(10, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > TREES_JUNGLE = register("trees_jungle", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.1F), JUNGLE_BUSH.withChance(0.5F), MEGA_JUNGLE_TREE.withChance(0.33333334F)), JUNGLE_TREE)).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(50, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > BAMBOO_VEGETATION = register("bamboo_vegetation", Feature.RANDOM_SELECTOR.withConfiguration(new MultipleRandomFeatureConfig(ImmutableList.of(FANCY_OAK.withChance(0.05F), JUNGLE_BUSH.withChance(0.15F), MEGA_JUNGLE_TREE.withChance(0.7F)), Feature.RANDOM_PATCH.withConfiguration(Features.Configs.JUNGLE_VEGETATION_CONFIG))).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.field_242902_f.configure(new AtSurfaceWithExtraConfig(30, 0.1F, 1))));
    public static final ConfiguredFeature <? , ? > MUSHROOM_FIELD_VEGETATION = register("mushroom_field_vegetation", Feature.RANDOM_BOOLEAN_SELECTOR.withConfiguration(new TwoFeatureChoiceConfig(() ->
    {
        return HUGE_RED_MUSHROOM;
    }, () ->
    {
        return HUGE_BROWN_MUSHROOM;
    })).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT));

    private static <FC extends IFeatureConfig> ConfiguredFeature < FC, ? > register(String key, ConfiguredFeature < FC, ? > configuredFeature)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, key, configuredFeature);
    }

    public static final class Configs
    {
        public static final BlockClusterFeatureConfig GRASS_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.GRASS), SimpleBlockPlacer.PLACER)).tries(32).build();
        public static final BlockClusterFeatureConfig TAIGA_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.GRASS, 1).addWeightedBlockstate(Features.States.FERN, 4), SimpleBlockPlacer.PLACER)).tries(32).build();
        public static final BlockClusterFeatureConfig JUNGLE_VEGETATION_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.GRASS, 3).addWeightedBlockstate(Features.States.FERN, 1), SimpleBlockPlacer.PLACER)).blacklist(ImmutableSet.of(Features.States.PODZOL)).tries(32).build();
        public static final BlockClusterFeatureConfig NORMAL_FLOWER_CONFIG = (new BlockClusterFeatureConfig.Builder((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.POPPY, 2).addWeightedBlockstate(Features.States.DANDELION, 1), SimpleBlockPlacer.PLACER)).tries(64).build();
        public static final BlockClusterFeatureConfig DEAD_BUSH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.DEAD_BUSH), SimpleBlockPlacer.PLACER)).tries(4).build();
        public static final BlockClusterFeatureConfig BERRY_BUSH_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SWEET_BERRY_BUSH), SimpleBlockPlacer.PLACER)).tries(64).whitelist(ImmutableSet.of(Features.States.GRASS_BLOCK.getBlock())).func_227317_b_().build();
        public static final BlockClusterFeatureConfig TALL_GRASS_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.TALL_GRASS), new DoublePlantBlockPlacer())).tries(64).func_227317_b_().build();
        public static final BlockClusterFeatureConfig SUGAR_CANE_PATCH_CONFIG = (new BlockClusterFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SUGAR_CANE), new ColumnBlockPlacer(2, 2))).tries(20).xSpread(4).ySpread(0).zSpread(4).func_227317_b_().requiresWater().build();
        public static final LiquidsConfig LAVA_SPRING_CONFIG = new LiquidsConfig(Features.States.LAVA, true, 4, 1, ImmutableSet.of(Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE));
        public static final LiquidsConfig CLOSED_SPRING_CONFIG = new LiquidsConfig(Features.States.LAVA, false, 5, 0, ImmutableSet.of(Blocks.NETHERRACK));
        public static final BlockStateProvidingFeatureConfig CRIMSON_FOREST_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.CRIMSON_ROOTS, 87).addWeightedBlockstate(Features.States.CRIMSON_FUNGUS, 11).addWeightedBlockstate(Features.States.WARPED_FUNGUS, 1));
        public static final BlockStateProvidingFeatureConfig WARPED_FOREST_VEGETATION_CONFIG = new BlockStateProvidingFeatureConfig((new WeightedBlockStateProvider()).addWeightedBlockstate(Features.States.WARPED_ROOTS, 85).addWeightedBlockstate(Features.States.CRIMSON_ROOTS, 1).addWeightedBlockstate(Features.States.WARPED_FUNGUS, 13).addWeightedBlockstate(Features.States.CRIMSON_FUNGUS, 1));
        public static final BlockStateProvidingFeatureConfig NETHER_SPROUTS_CONFIG = new BlockStateProvidingFeatureConfig(new SimpleBlockStateProvider(Features.States.NETHER_SPROUTS));
    }

    public static final class Placements
    {
        public static final BeehiveTreeDecorator BEES_0002_PLACEMENT = new BeehiveTreeDecorator(0.002F);
        public static final BeehiveTreeDecorator BEES_002_PLACEMENT = new BeehiveTreeDecorator(0.02F);
        public static final BeehiveTreeDecorator BEES_005_PLACEMENT = new BeehiveTreeDecorator(0.05F);
        public static final ConfiguredPlacement<FeatureSpreadConfig> FIRE_PLACEMENT = Placement.FIRE.configure(new FeatureSpreadConfig(10));
        public static final ConfiguredPlacement<NoPlacementConfig> FLOWER_TALL_GRASS_PLACEMENT = Placement.field_242904_h.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> KELP_PLACEMENT = Placement.TOP_SOLID_HEIGHTMAP.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> BAMBOO_PLACEMENT = Placement.field_242906_k.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<NoPlacementConfig> HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT = Placement.field_242905_i.configure(IPlacementConfig.NO_PLACEMENT_CONFIG);
        public static final ConfiguredPlacement<TopSolidRangeConfig> NETHER_SPRING_ORE_PLACEMENT = Placement.field_242907_l.configure(new TopSolidRangeConfig(10, 20, 128));
        public static final ConfiguredPlacement<TopSolidRangeConfig> SPRING_PLACEMENT = Placement.field_242907_l.configure(new TopSolidRangeConfig(4, 8, 128));
        public static final ConfiguredPlacement<?> VEGETATION_PLACEMENT = Placement.field_242911_p.configure(NoPlacementConfig.field_236556_b_);
        public static final ConfiguredPlacement<?> HEIGHTMAP_PLACEMENT = FLOWER_TALL_GRASS_PLACEMENT.func_242728_a();
        public static final ConfiguredPlacement<?> PATCH_PLACEMENT = HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT.func_242728_a();
        public static final ConfiguredPlacement<?> SEAGRASS_DISK_PLACEMENT = KELP_PLACEMENT.func_242728_a();
    }

    public static final class States
    {
        protected static final BlockState GRASS = Blocks.GRASS.getDefaultState();
        protected static final BlockState FERN = Blocks.FERN.getDefaultState();
        protected static final BlockState PODZOL = Blocks.PODZOL.getDefaultState();
        protected static final BlockState COARSE_DIRT = Blocks.COARSE_DIRT.getDefaultState();
        protected static final BlockState MYCELIUM = Blocks.MYCELIUM.getDefaultState();
        protected static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
        protected static final BlockState ICE = Blocks.ICE.getDefaultState();
        protected static final BlockState OAK_LOG = Blocks.OAK_LOG.getDefaultState();
        protected static final BlockState OAK_LEAVES = Blocks.OAK_LEAVES.getDefaultState();
        protected static final BlockState JUNGLE_LOG = Blocks.JUNGLE_LOG.getDefaultState();
        protected static final BlockState JUNGLE_LEAVES = Blocks.JUNGLE_LEAVES.getDefaultState();
        protected static final BlockState SPRUCE_LOG = Blocks.SPRUCE_LOG.getDefaultState();
        protected static final BlockState SPRUCE_LEAVES = Blocks.SPRUCE_LEAVES.getDefaultState();
        protected static final BlockState ACACIA_LOG = Blocks.ACACIA_LOG.getDefaultState();
        protected static final BlockState ACACIA_LEAVES = Blocks.ACACIA_LEAVES.getDefaultState();
        protected static final BlockState BIRCH_LOG = Blocks.BIRCH_LOG.getDefaultState();
        protected static final BlockState BIRCH_LEAVES = Blocks.BIRCH_LEAVES.getDefaultState();
        protected static final BlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
        protected static final BlockState DARK_OAK_LEAVES = Blocks.DARK_OAK_LEAVES.getDefaultState();
        protected static final BlockState GRASS_BLOCK = Blocks.GRASS_BLOCK.getDefaultState();
        protected static final BlockState LARGE_FERN = Blocks.LARGE_FERN.getDefaultState();
        protected static final BlockState TALL_GRASS = Blocks.TALL_GRASS.getDefaultState();
        protected static final BlockState LILAC = Blocks.LILAC.getDefaultState();
        protected static final BlockState ROSE_BUSH = Blocks.ROSE_BUSH.getDefaultState();
        protected static final BlockState PEONY = Blocks.PEONY.getDefaultState();
        protected static final BlockState BROWN_MUSHROOM = Blocks.BROWN_MUSHROOM.getDefaultState();
        protected static final BlockState RED_MUSHROOM = Blocks.RED_MUSHROOM.getDefaultState();
        protected static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
        protected static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();
        protected static final BlockState LILY_OF_THE_VALLEY = Blocks.LILY_OF_THE_VALLEY.getDefaultState();
        protected static final BlockState BLUE_ORCHID = Blocks.BLUE_ORCHID.getDefaultState();
        protected static final BlockState POPPY = Blocks.POPPY.getDefaultState();
        protected static final BlockState DANDELION = Blocks.DANDELION.getDefaultState();
        protected static final BlockState DEAD_BUSH = Blocks.DEAD_BUSH.getDefaultState();
        protected static final BlockState MELON = Blocks.MELON.getDefaultState();
        protected static final BlockState PUMPKIN = Blocks.PUMPKIN.getDefaultState();
        protected static final BlockState SWEET_BERRY_BUSH = Blocks.SWEET_BERRY_BUSH.getDefaultState().with(SweetBerryBushBlock.AGE, Integer.valueOf(3));
        protected static final BlockState FIRE = Blocks.FIRE.getDefaultState();
        protected static final BlockState SOUL_FIRE = Blocks.SOUL_FIRE.getDefaultState();
        protected static final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
        protected static final BlockState SOUL_SOIL = Blocks.SOUL_SOIL.getDefaultState();
        protected static final BlockState CRIMSON_ROOTS = Blocks.CRIMSON_ROOTS.getDefaultState();
        protected static final BlockState LILY_PAD = Blocks.LILY_PAD.getDefaultState();
        protected static final BlockState SNOW = Blocks.SNOW.getDefaultState();
        protected static final BlockState JACK_O_LANTERN = Blocks.JACK_O_LANTERN.getDefaultState();
        protected static final BlockState SUNFLOWER = Blocks.SUNFLOWER.getDefaultState();
        protected static final BlockState CACTUS = Blocks.CACTUS.getDefaultState();
        protected static final BlockState SUGAR_CANE = Blocks.SUGAR_CANE.getDefaultState();
        protected static final BlockState RED_MUSHROOM_BLOCK_DOWN = Blocks.RED_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
        protected static final BlockState BROWN_MUSHROOM_BLOCK_UP = Blocks.BROWN_MUSHROOM_BLOCK.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(true)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
        protected static final BlockState MUSHROOM_STEM = Blocks.MUSHROOM_STEM.getDefaultState().with(HugeMushroomBlock.UP, Boolean.valueOf(false)).with(HugeMushroomBlock.DOWN, Boolean.valueOf(false));
        protected static final FluidState WATER = Fluids.WATER.getDefaultState();
        protected static final FluidState LAVA = Fluids.LAVA.getDefaultState();
        protected static final BlockState WATER_BLOCK = Blocks.WATER.getDefaultState();
        protected static final BlockState LAVA_BLOCK = Blocks.LAVA.getDefaultState();
        protected static final BlockState DIRT = Blocks.DIRT.getDefaultState();
        protected static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
        protected static final BlockState GRANITE = Blocks.GRANITE.getDefaultState();
        protected static final BlockState DIORITE = Blocks.DIORITE.getDefaultState();
        protected static final BlockState ANDESITE = Blocks.ANDESITE.getDefaultState();
        protected static final BlockState COAL_ORE = Blocks.COAL_ORE.getDefaultState();
        protected static final BlockState IRON_ORE = Blocks.IRON_ORE.getDefaultState();
        protected static final BlockState GOLD_ORE = Blocks.GOLD_ORE.getDefaultState();
        protected static final BlockState REDSTONE_ORE = Blocks.REDSTONE_ORE.getDefaultState();
        protected static final BlockState DIAMOND_ORE = Blocks.DIAMOND_ORE.getDefaultState();
        protected static final BlockState LAPIS_ORE = Blocks.LAPIS_ORE.getDefaultState();
        protected static final BlockState STONE = Blocks.STONE.getDefaultState();
        protected static final BlockState EMERALD_ORE = Blocks.EMERALD_ORE.getDefaultState();
        protected static final BlockState INFESTED_STONE = Blocks.INFESTED_STONE.getDefaultState();
        protected static final BlockState SAND = Blocks.SAND.getDefaultState();
        protected static final BlockState CLAY = Blocks.CLAY.getDefaultState();
        protected static final BlockState MOSSY_COBBLESTONE = Blocks.MOSSY_COBBLESTONE.getDefaultState();
        protected static final BlockState SEAGRASS = Blocks.SEAGRASS.getDefaultState();
        protected static final BlockState MAGMA_BLOCK = Blocks.MAGMA_BLOCK.getDefaultState();
        protected static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
        protected static final BlockState NETHER_GOLD_ORE = Blocks.NETHER_GOLD_ORE.getDefaultState();
        protected static final BlockState NETHER_QUARTZ_ORE = Blocks.NETHER_QUARTZ_ORE.getDefaultState();
        protected static final BlockState BLACKSTONE = Blocks.BLACKSTONE.getDefaultState();
        protected static final BlockState ANCIENT_DEBRIS = Blocks.ANCIENT_DEBRIS.getDefaultState();
        protected static final BlockState BASALT = Blocks.BASALT.getDefaultState();
        protected static final BlockState CRIMSON_FUNGUS = Blocks.CRIMSON_FUNGUS.getDefaultState();
        protected static final BlockState WARPED_FUNGUS = Blocks.WARPED_FUNGUS.getDefaultState();
        protected static final BlockState WARPED_ROOTS = Blocks.WARPED_ROOTS.getDefaultState();
        protected static final BlockState NETHER_SPROUTS = Blocks.NETHER_SPROUTS.getDefaultState();
    }
}
