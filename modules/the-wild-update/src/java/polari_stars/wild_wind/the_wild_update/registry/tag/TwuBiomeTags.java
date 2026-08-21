package polari_stars.wild_wind.the_wild_update.registry.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;
import polari_stars.wild_wind.the_wild_update.Twu;

public interface TwuBiomeTags {
    /// 沼泥蟹 生成标签 寒带
    TagKey<Biome> SPAWNS_COLD_VARIANT_MUDCRABS = create("spawns_cold_variant_mudcrabs");
    /// 沼泥蟹 生成标签 热带
    TagKey<Biome> SPAWNS_WARM_VARIANT_MUDCRABS = create("spawns_warm_variant_mudcrabs");

    private static @NotNull TagKey<Biome> create(String name) {
        return TagKey.create(Registries.BIOME, Twu.namespace(name));
    }

    private static @NotNull TagKey<Biome> createC(String name) {
        return TagKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("c", name));
    }

    static void init() {
    }
}
