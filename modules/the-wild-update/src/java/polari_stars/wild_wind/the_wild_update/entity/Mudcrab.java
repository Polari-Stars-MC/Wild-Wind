package polari_stars.wild_wind.the_wild_update.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuSensorTypes;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;
import polari_stars.wild_wind.the_wild_update.registry.tag.TwuBiomeTags;

import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;

// TODO 还需要补充:
//  攻击代码
//  行为代码
//  潮湿代码
//  数据组件

/// 泥沼蟹
/// https://lcnmjxmuyybr.feishu.cn/wiki/VVAsw5qAeiyyzRku29acGUTmnMd
public class Mudcrab extends Animal implements Bucketable, GeoEntity {
    public static final double MAX_HEALTH = 10.0;
    /// 最大湿润值
    public static final int MOISTURE_MAX_VALUE = 20 * 60 * 6;
    /// 触发寻找水源行为的湿润值
    public static final int FINDING_WATER_SIRUANZ_VALUE = 20 * 60 * 2;
    /// 寻找水源的最大距离
    public static final int FINDING_WATER_MAX_DISTANCE = 20;
    /// 捕食寻找生物距离
    public static final int PREDATING_DISTANCE = 16;
    /// 捕食伤害
    public static final float DAMAGE = 10.0F;
    /// 跟随玩家距离
    public static final int FOLLOW_PLAYER_DISTANCE = 10;
    /// 繁殖冷却
    public static final int REPRODUCTION_COOLDOWN = 20 * 60 * 5;
    /// 喂食减少比例
    public static final float FEEDING_REDUCTION_RATIO = 0.1F;
    /// 生长时间
    public static final int GROWING_TICK = 20 * 60 * 20;
    /// 食物判断
    public static final Predicate<ItemStack> FOOD_PREDICATE = (itemStack1) -> itemStack1.is(Items.SPIDER_EYE);
    private static final Brain.Provider<Mudcrab> BRAIN_PROVIDER = Brain.provider(
            List.of(SensorType.NEAREST_LIVING_ENTITIES,
                    SensorType.NEAREST_ADULT,
                    SensorType.HURT_BY,
                    TwuSensorTypes.MUDCRAB_ATTACKABLES.get(),
                    SensorType.FOOD_TEMPTATIONS),
            var0 -> MudcrabAi.getActivities()
    );
    /// 变体值
    private static final EntityDataAccessor<Integer> DATA_VARIANT = SynchedEntityData.defineId(Mudcrab.class, EntityDataSerializers.INT);
    /// 是否是从桶中来
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Mudcrab.class, EntityDataSerializers.BOOLEAN);
    /// 潮湿值
    private static final EntityDataAccessor<Integer> MOISTNESS_LEVEL = SynchedEntityData.defineId(Mudcrab.class, EntityDataSerializers.INT);

    private final AnimatableInstanceCache animatCache = GeckoLibUtil.createInstanceCache(this);

    public Mudcrab(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public Mudcrab(Level level) {
        this(TwuEntityTypes.MUDCRAB.get(), level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE, DAMAGE);
    }

    @Override
    public void tick() {
        Level level = this.level();
        boolean isClient = level.isClientSide();
        super.tick();
        if (this.isNoAi()) {
            this.setMoisntessLevel(MOISTURE_MAX_VALUE);
            return;
        }

        // 潮湿环境
        if (this.isInWaterOrRain()) {
            if (!isClient) {
                this.setMoisntessLevel(MOISTURE_MAX_VALUE);
            }
            return;
        }

        // 不在潮湿环境

        if (!isClient) {
            this.setMoisntessLevel(this.getMoistnessLevel() - 1);
        }
    }

    /// TODO
    public static void onStopAttacking(ServerLevel level, Mudcrab body, LivingEntity target) {
//        if (!target.isDeadOrDying()) {
//            return;
//        }
//        DamageSource lastDamageSource = target.getLastDamageSource();
//        if (lastDamageSource == null
//                || !(lastDamageSource.getEntity() instanceof Player player)) {
//            return;
//        }
//        List<Player> playersInRange = level.getEntitiesOfClass(Player.class, body.getBoundingBox().inflate(20.0));
//        if (playersInRange.contains(player)) {
//        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float damage) {
        return super.hurtServer(level, source, damage);
    }

    @Override
    public Brain<Mudcrab> getBrain() {
        return (Brain<Mudcrab>) super.getBrain();
    }

    public int getMoistnessLevel() {
        return this.entityData.get(MOISTNESS_LEVEL);
    }

    public void setMoisntessLevel(int level) {
        this.entityData.set(MOISTNESS_LEVEL, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return FOOD_PREDICATE.test(itemStack);
    }

    @Override
    public boolean fromBucket() {
        return entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean fromBucket) {
        this.entityData.set(FROM_BUCKET, fromBucket);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_VARIANT, Variant.DEFAULT.id);
        entityData.define(FROM_BUCKET, false);
        entityData.define(MOISTNESS_LEVEL, MOISTURE_MAX_VALUE);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("Variant", Variant.CODEC, this.getVariant());
        output.putBoolean("FromBucket", this.fromBucket());
        output.putInt("Moistness", this.getMoistnessLevel());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setVariant(input.read("Variant", Variant.CODEC).orElse(Variant.DEFAULT));
        this.setFromBucket(input.getBooleanOr("FromBucket", false));
        this.setMoisntessLevel(input.getIntOr("Moistness", 2400));
    }

    @Override
    public SpawnGroupData finalizeSpawn(
            ServerLevelAccessor level, DifficultyInstance difficulty,
            EntitySpawnReason spawnReason, @Nullable SpawnGroupData groupData
    ) {
        if (spawnReason == EntitySpawnReason.BUCKET) {
            return groupData;
        }

        if (level.getRandom().nextInt(10) == 0) {
            this.setAge(-GROWING_TICK);
        }
        this.setVariant(Variant.getVariant(this.blockPosition(), level));

        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT));
    }

    private void setVariant(Variant variant) {
        this.entityData.set(DATA_VARIANT, variant.getId());
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || this.fromBucket();
    }

    @Override
    public boolean removeWhenFarAway(double distSqr) {
        return !this.fromBucket() && !this.hasCustomName();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        Mudcrab baby = TwuEntityTypes.MUDCRAB.get().create(level, EntitySpawnReason.BREEDING);
        if (baby != null) {
            Variant variant = Variant.getVariant(baby.blockPosition(), level);

            baby.setVariant(variant);
            baby.setPersistenceRequired();
        }

        return baby;
    }

    @Override
    public void saveToBucketTag(ItemStack bucket) {

    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {

    }

    @Override
    public ItemStack getBucketItemStack() {
        // TODO 补充桶装泥沼蟹物品
        return ItemStack.EMPTY;
    }

    @Override
    public SoundEvent getPickupSound() {
        // TODO 补充泥沼蟹的拾取音效
        return SoundEvents.BUCKET_FILL_FISH;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animatCache;
    }

    public enum Variant implements StringRepresentable {
        /// 寒带
        COLD(0, "cold"),
        /// 热带
        WARM(1, "warm"),
        /// 温带
        TEMPERATE(2, "temperate");

        public static final Variant DEFAULT = TEMPERATE;
        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Variant> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, Variant::getId);
        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);
        private final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static Variant byId(int id) {
            return BY_ID.apply(id);
        }

        public static Variant getVariant(BlockPos pos, LevelReader level) {
            Holder<Biome> biome = level.getBiome(pos);
            if (biome.is(TwuBiomeTags.SPAWNS_COLD_VARIANT_MUDCRABS)) {
                return COLD;
            }
            if (biome.is(TwuBiomeTags.SPAWNS_WARM_VARIANT_MUDCRABS)) {
                return WARM;
            }
            return TEMPERATE;
        }
    }
}
