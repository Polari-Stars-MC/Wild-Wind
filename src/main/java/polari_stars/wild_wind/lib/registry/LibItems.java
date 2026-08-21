package polari_stars.wild_wind.lib.registry;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public interface LibItems {
    static <I extends Item> DeferredItem<I> register(
            DeferredRegister.Items register,
            String name, String enUs, String zhCn,
            Function<Item.Properties, ? extends I> func
    ) {
        return register(register, name, enUs, zhCn, func, UnaryOperator.identity());
    }

    static <I extends Item> DeferredItem<I> register(
            DeferredRegister.Items register,
            String name, String enUs, String zhCn,
            Function<Item.Properties, ? extends I> func, UnaryOperator<Item.Properties> properties
    ) {
        DeferredItem<I> holder = register.registerItem(name, func, properties);
        LangHandler.addLangEnUsAndZhCnTxt(register.getNamespace(), enUs, zhCn,
                (langSet, txt) -> langSet.itemText(holder, txt));
        return holder;
    }
}
