package polari_stars.wild_wind.the_wild_update.registry;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuBiomeModifiers;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;

import java.util.List;

public interface TwuRegistrySets {
    RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, TwuRegistrySets::addMudcrabSpawns);

    private static void addMudcrabSpawns(BootstrapContext<BiomeModifier> bootstrap) {
        HolderGetter<Biome> biomes = bootstrap.lookup(Registries.BIOME);
        bootstrap.register(TwuBiomeModifiers.ADD_MUDCRAB_SPAWNS, new BiomeModifiers.AddSpawnsBiomeModifier(new AndHolderSet<>(
                biomes.getOrThrow(Tags.Biomes.IS_SWAMP)
        ), WeightedList.of(List.of(
                new Weighted<>(new MobSpawnSettings.SpawnerData(TwuEntityTypes.MUDCRAB.get(), 1, 2), 100)
        ))));
    }
}
