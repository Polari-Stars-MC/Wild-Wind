package polari_stars.wild_wind.lib.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface LibDataComponentTypes {
    static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(
            DeferredRegister<DataComponentType<?>> register, String name,
            UnaryOperator<DataComponentType.Builder<T>> builder
    ) {
        return register.register(name, () -> builder.apply(DataComponentType.builder()).build());
    }
}
