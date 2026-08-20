package polari_stars.wild_wind.lib.registry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;

import java.util.function.UnaryOperator;

public class LibCreativeModeTabs {
    public static DeferredHolder<CreativeModeTab, CreativeModeTab> register(
            DeferredRegister<CreativeModeTab> registry,
            String name, String enUs, String zhCn,
            UnaryOperator<CreativeModeTab.Builder> builder,
            CreativeModeTab.DisplayItemsGenerator displayItemsGenerator
    ) {
        DeferredHolder<CreativeModeTab, CreativeModeTab> register = registry.register(name, () -> builder.apply(CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + registry.getNamespace() + "." + name))
                .displayItems(displayItemsGenerator)).build());
        String namespace = registry.getNamespace();
        var key = "itemGroup." + namespace + "." + name;
        LangHandler.addLangEnUsAndZhCnTxt(namespace, enUs, zhCn,
                (langSet, txt) -> langSet.add(key, txt));
        return register;
    }

    public static void addRegistryItem(DeferredRegister.Items registry, CreativeModeTab.Output output) {
        registry.getEntries().forEach(entry -> output.accept(entry.get()));
    }
}
