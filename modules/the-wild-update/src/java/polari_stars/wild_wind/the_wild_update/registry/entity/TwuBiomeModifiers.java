package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jspecify.annotations.NonNull;
import polari_stars.wild_wind.the_wild_update.Twu;

public interface TwuBiomeModifiers {
    ResourceKey<BiomeModifier> ADD_MUDCRAB_SPAWNS = create("mudcrab_spawns");

    private static @NonNull ResourceKey<BiomeModifier> create(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Twu.namespace(name));
    }
}
