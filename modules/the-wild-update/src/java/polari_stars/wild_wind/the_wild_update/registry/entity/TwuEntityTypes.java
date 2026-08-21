package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.registry.LibEntityTypes;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.Mudcrab;

public interface TwuEntityTypes {
    DeferredRegister.Entities REGISTER = DeferredRegister.createEntities(Twu.MODID);

    DeferredHolder<EntityType<?>, EntityType<Mudcrab>> MUDCRAB = LibEntityTypes.register(REGISTER,
            "mudcrab", "Mud Crab", "泥沼蟹", Mudcrab::new, MobCategory.WATER_CREATURE, builder -> builder
                    .sized(0.55f, 0.5f)
                    .eyeHeight(0.45f));

    static void init(IEventBus iEventBus) {
        REGISTER.register(iEventBus);
    }
}
