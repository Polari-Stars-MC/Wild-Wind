package polari_stars.wild_wind.the_wild_update.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import polari_stars.wild_wind.the_wild_update.Twu;

public interface TwuBlockTags {
    private static @NotNull TagKey<Block> create(String name) {
        return TagKey.create(Registries.BLOCK, Twu.namespace(name));
    }

    private static @NotNull TagKey<Block> createC(String name) {
            return TagKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath("c", name));
    }

    static void init() {

    }
}
