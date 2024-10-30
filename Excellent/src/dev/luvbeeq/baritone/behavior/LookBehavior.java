package dev.luvbeeq.baritone.behavior;

import dev.excellent.impl.util.rotation.RotationUtil;
import dev.luvbeeq.baritone.Baritone;
import dev.luvbeeq.baritone.api.Settings;
import dev.luvbeeq.baritone.api.behavior.ILookBehavior;
import dev.luvbeeq.baritone.api.behavior.look.IAimProcessor;
import dev.luvbeeq.baritone.api.behavior.look.ITickableAimProcessor;
import dev.luvbeeq.baritone.api.event.events.*;
import dev.luvbeeq.baritone.api.utils.IPlayerContext;
import dev.luvbeeq.baritone.api.utils.Rotation;
import dev.luvbeeq.baritone.behavior.look.ForkableRandom;
import net.minecraft.network.play.client.CPlayerPacket;

import java.util.Optional;

public final class LookBehavior extends Behavior implements ILookBehavior {
    /**
     * The current look target, may be {@code null}.
     */
    private Target target;

    /**
     * The rotation known to the server. Returned by {@link #getEffectiveRotation()} for use in {@link IPlayerContext}.
     */
    private Rotation serverRotation;

    /**
     * The last player rotation. Used to restore the player's angle when using free look.
     *
     * @see Settings#freeLook
     */
    private Rotation prevRotation;

    private final AimProcessor processor;

    public LookBehavior(Baritone baritone) {
        super(baritone);
        this.processor = new AimProcessor(baritone.getPlayerContext());
    }

    @Override
    public void updateTarget(Rotation rotation, boolean blockInteract) {
        this.target = new Target(rotation, blockInteract);
    }

    @Override
    public IAimProcessor getAimProcessor() {
        return this.processor;
    }

    @Override
    public void onTick(TickEvent event) {
        if (event.getType() == TickEvent.Type.IN) {
            this.processor.tick();
        }
    }

    @Override
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (this.target == null) {
            return;
        }
        switch (event.getState()) {
            case PRE: {
                if (this.target.mode == Target.Mode.NONE) {
                    // Just return for PRE, we still want to set target to null on POST
                    return;
                }
                if (this.target.mode == Target.Mode.SERVER) {
                    this.prevRotation = new Rotation(ctx.player().rotationYaw, ctx.player().rotationPitch);
                }

                final Rotation actual = this.processor.peekRotation(this.target.rotation);
                ctx.player().rotationYaw = actual.getYaw();
                ctx.player().rotationPitch = actual.getPitch();
                break;
            }
            case POST: {
                // Reset the player's rotations back to their original values
                if (this.prevRotation != null) {
                    ctx.player().rotationYaw = this.prevRotation.getYaw();
                    ctx.player().rotationPitch = this.prevRotation.getPitch();
                    this.prevRotation = null;
                }
                // The target is done being used for this game tick, so it can be invalidated
                this.target = null;
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onSendPacket(PacketEvent event) {
        if (!(event.getPacket() instanceof CPlayerPacket packet)) {
            return;
        }

        if (packet instanceof CPlayerPacket.RotationPacket || packet instanceof CPlayerPacket.PositionRotationPacket) {
            this.serverRotation = new Rotation(packet.getYaw(0.0f), packet.getPitch(0.0f));
        }
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        this.serverRotation = null;
        this.target = null;
    }

    public void pig() {
        if (this.target != null) {
            final Rotation actual = this.processor.peekRotation(this.target.rotation);
            ctx.player().rotationYaw = actual.getYaw();
        }
    }

    public Optional<Rotation> getEffectiveRotation() {
        if (Baritone.settings().freeLook.value) {
            return Optional.ofNullable(this.serverRotation);
        }
        // If freeLook isn't on, just defer to the player's actual rotations
        return Optional.empty();
    }

    @Override
    public void onPlayerRotationMove(RotationMoveEvent event) {
        if (this.target != null) {
            final Rotation actual = this.processor.peekRotation(this.target.rotation);
            ctx.player().rotationYawHead = actual.getYaw();
            ctx.player().renderYawOffset = RotationUtil.calculateCorrectYawOffset(actual.getYaw());
            ctx.player().rotationPitchHead = actual.getPitch();
            event.setYaw(actual.getYaw());
            event.setPitch(actual.getPitch());
        }
    }

    private static final class AimProcessor extends AbstractAimProcessor {

        public AimProcessor(final IPlayerContext ctx) {
            super(ctx);
        }

        @Override
        protected Rotation getPrevRotation() {
            // Implementation will use LookBehavior.serverRotation
            return ctx.playerRotations();
        }
    }

    private static abstract class AbstractAimProcessor implements ITickableAimProcessor {

        protected final IPlayerContext ctx;
        private final ForkableRandom rand;
        private double randomYawOffset;
        private double randomPitchOffset;

        public AbstractAimProcessor(IPlayerContext ctx) {
            this.ctx = ctx;
            this.rand = new ForkableRandom();
        }

        private AbstractAimProcessor(final AbstractAimProcessor source) {
            this.ctx = source.ctx;
            this.rand = source.rand.fork();
            this.randomYawOffset = source.randomYawOffset;
            this.randomPitchOffset = source.randomPitchOffset;
        }

        @Override
        public final Rotation peekRotation(final Rotation rotation) {
            final Rotation prev = this.getPrevRotation();

            float desiredYaw = rotation.getYaw();
            float desiredPitch = rotation.getPitch();

            // In other words, the target doesn't care about the pitch, so it used playerRotations().getPitch()
            // and it's safe to adjust it to a normal level
            if (desiredPitch == prev.getPitch()) {
                desiredPitch = nudgeToLevel(desiredPitch);
            }

            desiredYaw += this.randomYawOffset;
            desiredPitch += this.randomPitchOffset;

            return new Rotation(
                    this.calculateMouseMove(prev.getYaw(), desiredYaw),
                    this.calculateMouseMove(prev.getPitch(), desiredPitch)
            ).clamp();
        }

        @Override
        public final void tick() {
            // randomLooking
            this.randomYawOffset = (this.rand.nextDouble() - 0.5) * Baritone.settings().randomLooking.value;
            this.randomPitchOffset = (this.rand.nextDouble() - 0.5) * Baritone.settings().randomLooking.value;

            // randomLooking113
            double random = this.rand.nextDouble() - 0.5;
            if (Math.abs(random) < 0.1) {
                random *= 4;
            }
            this.randomYawOffset += random * Baritone.settings().randomLooking113.value;
        }

        @Override
        public final void advance(int ticks) {
            for (int i = 0; i < ticks; i++) {
                this.tick();
            }
        }

        @Override
        public Rotation nextRotation(final Rotation rotation) {
            final Rotation actual = this.peekRotation(rotation);
            this.tick();
            return actual;
        }

        @Override
        public final ITickableAimProcessor fork() {
            return new AbstractAimProcessor(this) {

                private Rotation prev = AbstractAimProcessor.this.getPrevRotation();

                @Override
                public Rotation nextRotation(final Rotation rotation) {
                    return (this.prev = super.nextRotation(rotation));
                }

                @Override
                protected Rotation getPrevRotation() {
                    return this.prev;
                }
            };
        }

        protected abstract Rotation getPrevRotation();

        /**
         * Nudges the player's pitch to a regular level. (Between {@code -20} and {@code 10}, increments are by {@code 1})
         */
        private float nudgeToLevel(float pitch) {
            if (pitch < -20) {
                return pitch + 1;
            } else if (pitch > 10) {
                return pitch - 1;
            }
            return pitch;
        }

        // The game uses rotation = (float) ((double) rotation + delta) so we'll do that as well
        private float calculateMouseMove(float current, float target) {
            final double delta = target - current;
            final double deltaPx = angleToMouse(delta); // yes, even the mouse movements use double
            return (float) ((double) current + mouseToAngle(deltaPx));
        }

        private double angleToMouse(double angleDelta) {
            final double minAngleChange = mouseToAngle(1);
            return Math.round(angleDelta / minAngleChange);
        }

        private double mouseToAngle(double mouseDelta) {
            // casting float literals to double gets us the precise values used by mc
            final double f = ctx.minecraft().gameSettings.mouseSensitivity * (double) 0.6f + (double) 0.2f;
            return mouseDelta * f * f * f * 8.0d * 0.15d;
        }
    }

    private static class Target {

        public final Rotation rotation;
        public final Mode mode;

        public Target(Rotation rotation, boolean blockInteract) {
            this.rotation = rotation;
            this.mode = Mode.resolve(blockInteract);
        }

        enum Mode {
            /**
             * Rotation will be set client-side and is visual to the player
             */
            CLIENT,

            /**
             * Rotation will be set server-side and is silent to the player
             */
            SERVER,

            /**
             * Rotation will remain unaffected on both the client and server
             */
            NONE;

            static Mode resolve(boolean blockInteract) {
                final Settings settings = Baritone.settings();
                final boolean antiCheat = settings.antiCheatCompatibility.value;
                final boolean blockFreeLook = settings.blockFreeLook.value;
                final boolean freeLook = settings.freeLook.value;

                if (!freeLook) return CLIENT;
                if (!blockFreeLook && blockInteract) return CLIENT;

                // Regardless of if antiCheatCompatibility is enabled, if a blockInteract is requested then the player
                // rotation needs to be set somehow, otherwise Baritone will halt since objectMouseOver() will just be
                // whatever the player is mousing over visually. Let's just settle for setting it silently.
                if (antiCheat || blockInteract) return SERVER;

                // Pathing regularly without antiCheatCompatibility, don't set the player rotation
                return NONE;
            }
        }
    }
}
