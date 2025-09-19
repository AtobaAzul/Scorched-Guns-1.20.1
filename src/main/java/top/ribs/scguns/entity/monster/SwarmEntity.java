package top.ribs.scguns.entity.monster;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.ribs.scguns.init.ModEntities;
import top.ribs.scguns.init.ModTags;

import java.util.EnumSet;
import java.util.List;

public class SwarmEntity extends FlyingMob implements Enemy {
    private static final int LIFESPAN_TICKS = 1200;
    private int lifespan;

    public SwarmEntity(EntityType<? extends SwarmEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new SwarmMoveGoal(this, 0.5f, 0.7f);
        this.lookControl = new SwarmLookGoal(this);
        this.lifespan = LIFESPAN_TICKS;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    @Override
    public boolean isAlive() {
        return !this.isDeadOrDying() && super.isAlive();
    }

    private boolean isActive = true;

    @Override
    public void die(DamageSource cause) {
        super.die(cause);
        this.isActive = false;
    }

    public boolean isActive() {
        return this.isActive;
    }

    @Override
    public boolean isAffectedByPotions() {
        return true;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (--this.lifespan <= 0) {
                this.discard();
            }
        }
        if (this.level().isClientSide()) {
            setupAnimationStates();
        }
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if (this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6F, 1f);
        } else {
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FollowHiveGoal(this, 1.0D, 10.0F));
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Monster.class, true) {
            @Override
            public boolean canUse() {
                return super.canUse() && isValidTarget(this.target);
            }
        });

        this.goalSelector.addGoal(1, new SwarmAttackGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6D)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.0f)
                .add(Attributes.ATTACK_DAMAGE, 1f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BEE_LOOP;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.BEE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    // Method to check if a target is valid using the tag system
    private boolean isValidTarget(LivingEntity target) {
        if (target == null) {
            return false;
        }
        // Check if the target's entity type is in the NON_SWARM_TARGETED tag
        return !target.getType().is(ModTags.Entities.NON_SWARM_TARGETED);
    }

    public static class SwarmLookGoal extends LookControl {
        public SwarmLookGoal(Mob mob) {
            super(mob);
        }

        @Override
        public void tick() {
            if (this.mob.getTarget() != null) {
                this.mob.lookAt(this.mob.getTarget(), 30.0F, 30.0F);
            }
        }
    }

    public class SwarmMoveGoal extends MoveControl {
        private final Mob mob;
        private final float speed;
        private final double maxSpeed;

        public SwarmMoveGoal(Mob mob, float speed, double maxSpeed) {
            super(mob);
            this.mob = mob;
            this.speed = speed;
            this.maxSpeed = maxSpeed;
        }

        @Override
        public void tick() {
            if (this.operation == Operation.MOVE_TO && this.mob.getTarget() != null) {
                LivingEntity target = this.mob.getTarget();
                double dx = target.getX() - this.mob.getX();
                double dy = target.getY() - this.mob.getY() + (double) (target.getBbHeight() / 2.0F);
                double dz = target.getZ() - this.mob.getZ();
                double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

                if (distance < 10.0) {
                    Vec3 desiredMovement = new Vec3(
                            dx / distance * this.speed,
                            dy / distance * this.speed,
                            dz / distance * this.speed
                    );
                    Vec3 currentMovement = this.mob.getDeltaMovement();
                    Vec3 newMovement = currentMovement.add(desiredMovement.subtract(currentMovement).scale(0.2));
                    if (newMovement.length() > this.maxSpeed) {
                        newMovement = newMovement.normalize().scale(this.maxSpeed);
                    }

                    this.mob.setDeltaMovement(newMovement);
                }
            }
        }
    }

    public class SwarmAttackGoal extends Goal {
        private final SwarmEntity swarm;
        private final double speedTowardsTarget;
        private int attackCooldown;

        public SwarmAttackGoal(SwarmEntity swarm, double speedTowardsTarget) {
            this.swarm = swarm;
            this.speedTowardsTarget = speedTowardsTarget;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.swarm.getTarget();
            return target != null && target.isAlive() && isValidTarget(target);
        }

        @Override
        public void tick() {
            LivingEntity target = this.swarm.getTarget();
            if (target != null && this.swarm.distanceToSqr(target) < 9.0D) {
                if (this.attackCooldown <= 0) {
                    this.swarm.doHurtTarget(target);
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0));
                    this.attackCooldown = 20;
                } else {
                    this.attackCooldown--;
                }
            }
            this.swarm.getMoveControl().setWantedPosition(target.getX(), target.getY(), target.getZ(), this.speedTowardsTarget);
        }

        private boolean isValidTarget(LivingEntity target) {
            return !target.getType().is(ModTags.Entities.NON_SWARM_TARGETED);
        }
    }

    public class FollowHiveGoal extends Goal {
        private final SwarmEntity swarm;
        private final double followSpeed;
        private final float maxDist;
        private HiveEntity nearestHive;

        public FollowHiveGoal(SwarmEntity swarm, double followSpeed, float maxDist) {
            this.swarm = swarm;
            this.followSpeed = followSpeed;
            this.maxDist = maxDist;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.swarm.getTarget() == null && this.findNearestHive();
        }

        @Override
        public boolean canContinueToUse() {
            return this.swarm.getTarget() == null && this.nearestHive != null && this.nearestHive.isAlive() && this.swarm.distanceToSqr(this.nearestHive) > (double)(this.maxDist * this.maxDist);
        }

        @Override
        public void start() {
            this.swarm.getNavigation().moveTo(this.nearestHive, this.followSpeed);
        }

        @Override
        public void stop() {
            this.nearestHive = null;
            this.swarm.getNavigation().stop();
        }

        @Override
        public void tick() {
            if (this.swarm.distanceToSqr(this.nearestHive) > (double)(this.maxDist * this.maxDist)) {
                this.swarm.getNavigation().moveTo(this.nearestHive, this.followSpeed);
            } else {
                this.swarm.getNavigation().stop();
            }
        }

        private boolean findNearestHive() {
            List<HiveEntity> hiveList = this.swarm.level().getEntitiesOfClass(HiveEntity.class, this.swarm.getBoundingBox().inflate(this.maxDist));
            if (hiveList.isEmpty()) {
                return false;
            } else {
                this.nearestHive = hiveList.get(0);
                return true;
            }
        }
    }
}