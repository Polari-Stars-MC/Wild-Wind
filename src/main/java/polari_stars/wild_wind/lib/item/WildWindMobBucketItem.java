package polari_stars.wild_wind.lib.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class WildWindMobBucketItem extends MobBucketItem {
    private final Supplier<EntityType<? extends Mob>> supplierType;
    private EntityType<? extends Mob> type;

    public WildWindMobBucketItem(Supplier<EntityType<? extends Mob>> type, Fluid content, SoundEvent emptySound, Properties properties) {
        super(null, content, emptySound, properties);
        this.supplierType = type;
    }

    @Override
    public void checkExtraContent(@Nullable LivingEntity user, Level level, ItemStack itemStack, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            this.spawn(serverLevel, itemStack, pos);
            level.gameEvent(user, GameEvent.ENTITY_PLACE, pos);
        }
    }

    private void spawn(ServerLevel level, ItemStack itemStack, BlockPos spawnPos) {
        Mob mob = getType().create(level, EntityType.createDefaultStackConfig(level, itemStack, null), spawnPos, EntitySpawnReason.BUCKET, true, false);
        if (mob instanceof Bucketable bucketable) {
            CustomData entityData = itemStack.getOrDefault(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY);
            bucketable.loadFromBucketTag(entityData.copyTag());
            bucketable.setFromBucket(true);
        }

        if (mob != null) {
            level.addFreshEntityWithPassengers(mob);
            mob.playAmbientSound();
        }
    }

    public EntityType<? extends Mob> getType() {
        if (type == null) {
            type = supplierType.get();
        }
        return type;
    }
}
