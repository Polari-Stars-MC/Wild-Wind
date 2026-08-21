package polari_stars.wild_wind.the_wild_update.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.animal.axolotl.ValidatePlayDead;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.level.Level;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;

import java.util.List;
import java.util.Optional;

public class MudcrabAi {

    private static final UniformInt ADULT_FOLLOW_RANGE = UniformInt.of(5, 16);
    private static final float SPEED_MULTIPLIER_WHEN_MAKING_LOVE = 0.2F;
    private static final float SPEED_MULTIPLIER_ON_LAND = 0.15F;
    private static final float SPEED_MULTIPLIER_WHEN_IDLING_IN_WATER = 0.5F;
    private static final float SPEED_MULTIPLIER_WHEN_CHASING_IN_WATER = 0.6F;
    private static final float SPEED_MULTIPLIER_WHEN_FOLLOWING_ADULT_IN_WATER = 0.6F;

    protected static List<ActivityData<Mudcrab>> getActivities() {
        return List.of(initCoreActivity(), initIdleActivity(), initFightActivity(), initPlayDeadActivity());
    }

    protected static ActivityData<Mudcrab> initPlayDeadActivity() {
        return ActivityData.create(
                Activity.PLAY_DEAD,
                ImmutableList.of(Pair.of(0, EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.PLAY_DEAD_TICKS))),
                ImmutableSet.of(Pair.of(MemoryModuleType.PLAY_DEAD_TICKS, MemoryStatus.VALUE_PRESENT)),
                ImmutableSet.of(MemoryModuleType.PLAY_DEAD_TICKS)
        );
    }

    protected static ActivityData<Mudcrab> initFightActivity() {
        return ActivityData.create(
                Activity.FIGHT,
                0,
                ImmutableList.of(
                        StopAttackingIfTargetInvalid.create(Mudcrab::onStopAttacking),
                        SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(MudcrabAi::getSpeedModifierChasing),
                        MeleeAttack.create(20),
                        EraseMemoryIf.create(BehaviorUtils::isBreeding, MemoryModuleType.ATTACK_TARGET)
                ),
                MemoryModuleType.ATTACK_TARGET
        );
    }

    protected static ActivityData<Mudcrab> initCoreActivity() {
        return ActivityData.create(
                Activity.CORE,
                0,
                ImmutableList.of(
                        new LookAtTargetSink(45, 90),
                        new MoveToTargetSink(),
                        ValidatePlayDead.create(),
                        new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS)
                )
        );
    }

    protected static ActivityData<Mudcrab> initIdleActivity() {
        return ActivityData.create(
                Activity.IDLE,
                ImmutableList.of(
                        Pair.of(0, SetEntityLookTargetSometimes.create(EntityTypes.PLAYER, 6.0F, UniformInt.of(30, 60))),
                        Pair.of(1, new AnimalMakeLove(TwuEntityTypes.MUDCRAB.get(), 0.2F, 2)),
                        Pair.of(
                                2,
                                new RunOne<>(
                                        ImmutableList.of(
                                                Pair.of(new FollowTemptation(MudcrabAi::getSpeedModifier), 1),
                                                Pair.of(
                                                        BabyFollowAdult.create(
                                                                ADULT_FOLLOW_RANGE, MudcrabAi::getSpeedModifierFollowingAdult, MemoryModuleType.NEAREST_VISIBLE_ADULT, false
                                                        ),
                                                        1
                                                )
                                        )
                                )
                        ),
                        Pair.of(3, StartAttacking.create(MudcrabAi::findNearestValidAttackTarget)),
                        Pair.of(3, TryFindWater.create(6, 0.15F)),
                        Pair.of(
                                4,
                                new GateBehavior<>(
                                        ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                                        ImmutableSet.of(),
                                        GateBehavior.OrderPolicy.ORDERED,
                                        GateBehavior.RunningPolicy.TRY_ALL,
                                        ImmutableList.of(
                                                Pair.of(RandomStroll.swim(0.5F), 2),
                                                Pair.of(RandomStroll.stroll(0.15F, false), 2),
                                                Pair.of(SetWalkTargetFromLookTarget.create(MudcrabAi::canSetWalkTargetFromLookTarget, MudcrabAi::getSpeedModifier, 3), 3),
                                                Pair.of(BehaviorBuilder.triggerIf(Entity::isInWater), 5),
                                                Pair.of(BehaviorBuilder.triggerIf(Entity::onGround), 5)
                                        )
                                )
                        )
                )
        );
    }

    private static boolean canSetWalkTargetFromLookTarget(LivingEntity body) {
        Level level = body.level();
        Optional<PositionTracker> lookTarget = body.getBrain().getMemory(MemoryModuleType.LOOK_TARGET);
        if (lookTarget.isPresent()) {
            BlockPos pos = lookTarget.get().currentBlockPosition();
            return level.isWaterAt(pos) == body.isInWater();
        } else {
            return false;
        }
    }

    public static void updateActivity(Mudcrab body) {
        Brain<Mudcrab> brain = body.getBrain();
        Activity oldActivity = brain.getActiveNonCoreActivity().orElse(null);
        if (oldActivity != Activity.PLAY_DEAD) {
            brain.setActiveActivityToFirstValid(ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
            if (oldActivity == Activity.FIGHT && brain.getActiveNonCoreActivity().orElse(null) != Activity.FIGHT) {
                brain.setMemoryWithExpiry(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
            }
        }
    }

    private static float getSpeedModifierChasing(LivingEntity mob) {
        return mob.isInWater() ? 0.6F : 0.15F;
    }

    private static float getSpeedModifierFollowingAdult(LivingEntity mob) {
        return mob.isInWater() ? 0.6F : 0.15F;
    }

    private static float getSpeedModifier(LivingEntity mob) {
        return mob.isInWater() ? 0.5F : 0.15F;
    }

    private static Optional<? extends LivingEntity> findNearestValidAttackTarget(ServerLevel level, Mudcrab body) {
        return BehaviorUtils.isBreeding(body) ? Optional.empty() : body.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }
}
