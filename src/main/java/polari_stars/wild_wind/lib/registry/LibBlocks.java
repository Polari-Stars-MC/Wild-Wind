package polari_stars.wild_wind.lib.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class LibBlocks {
    public static <B extends Block> DeferredBlock<B> register(
            DeferredRegister.Blocks register,
            String name, String enUs, String zhCn,
            Function<BlockBehaviour.Properties, ? extends B> func,
            UnaryOperator<BlockBehaviour.Properties> properties
    ) {
        DeferredBlock<B> holder = register.registerBlock(name, func, properties);
        LangHandler.addLangEnUsAndZhCnTxt(register.getNamespace(), enUs, zhCn,
                (langSet, txt) -> langSet.blockText(holder, txt));
        return holder;
    }

    public static <B extends Block> DeferredBlock<B> register(
            DeferredRegister.Blocks register,
            String name, String enUs, String zhCn,
            Function<BlockBehaviour.Properties, ? extends B> func
    ) {
        return register(register, name, enUs, zhCn, func, UnaryOperator.identity());
    }
}
