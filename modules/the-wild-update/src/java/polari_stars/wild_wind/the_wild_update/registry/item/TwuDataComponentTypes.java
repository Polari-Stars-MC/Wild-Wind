package polari_stars.wild_wind.the_wild_update.registry.item;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.registry.LibDataComponentTypes;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.Mudcrab;

public interface TwuDataComponentTypes {
    DeferredRegister<DataComponentType<?>> REGISTER = Twu.register(BuiltInRegistries.DATA_COMPONENT_TYPE);

    DeferredHolder<DataComponentType<?>, DataComponentType<Mudcrab.Variant>> MUDCRAB_VARIANT = LibDataComponentTypes.register(REGISTER,
            "mudcrab/variant", b -> b.persistent(Mudcrab.Variant.CODEC).networkSynchronized(Mudcrab.Variant.STREAM_CODEC));

    static void init(IEventBus iEventBus){
        REGISTER.register(iEventBus);
    }
}
