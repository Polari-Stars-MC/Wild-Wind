package polari_stars.wild_wind.lib.registry;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;

import java.util.function.UnaryOperator;

public class LibEntity {
    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
            DeferredRegister.Entities register,
            String name, String enUs, String zhCn,
            EntityType.EntityFactory<T> factory,
            MobCategory category, UnaryOperator<EntityType.Builder<T>> builder
    ) {
        var holder = register.registerEntityType(name, factory, category, builder);
        LangHandler.addLangEnUsAndZhCnTxt(register.getNamespace(), enUs, zhCn,
                (langSet, txt) -> langSet.entityTypeText(holder, txt));
        return holder;
    }

    public static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(
            DeferredRegister.Entities register,
            String name, String enUs, String zhCn,
            EntityType.EntityFactory<T> factory, MobCategory category
    ) {
        return register(register, name, enUs, zhCn, factory, category, UnaryOperator.identity());
    }
}
