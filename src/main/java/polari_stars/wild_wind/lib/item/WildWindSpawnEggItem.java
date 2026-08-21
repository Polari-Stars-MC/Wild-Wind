package polari_stars.wild_wind.lib.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class WildWindSpawnEggItem extends SpawnEggItem {
    private final Supplier<EntityType<?>> supplierType;
    private EntityType<?> type;

    public WildWindSpawnEggItem(Supplier<EntityType<?>> type, Properties properties) {
        super(properties);
        this.supplierType = type;
    }

    public EntityType<? extends Entity> getType() {
        if (type == null) {
            type = supplierType.get();
        }
        return type;
    }
}
