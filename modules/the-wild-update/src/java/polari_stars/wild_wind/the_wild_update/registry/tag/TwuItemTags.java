package polari_stars.wild_wind.the_wild_update.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import polari_stars.wild_wind.the_wild_update.Twu;

public interface TwuItemTags {
    private static @NotNull TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, Twu.namespace(name));
    }

    private static @NotNull TagKey<Item> createC(String name) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath("c", name));
    }

    static void init() {

    }
}
