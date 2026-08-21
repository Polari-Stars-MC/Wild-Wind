package polari_stars.wild_wind.the_wild_update.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import polari_stars.wild_wind.the_wild_update.Twu;

public interface TwuEntityTypeTags {
    private static @NotNull TagKey<EntityType<?>> create(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Twu.namespace(name));
    }

    private static @NotNull TagKey<EntityType<?>> createC(String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath("c", name));
    }

    static void init() {

    }
}
