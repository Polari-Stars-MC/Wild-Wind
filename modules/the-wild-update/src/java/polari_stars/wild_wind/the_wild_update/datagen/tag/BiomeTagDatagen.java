package polari_stars.wild_wind.the_wild_update.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.registry.tag.TwuBiomeTags;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class BiomeTagDatagen extends BiomeTagsProvider {
    public BiomeTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Twu.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(TwuBiomeTags.SPAWNS_COLD_VARIANT_MUDCRABS)
                .addTag(Tags.Biomes.IS_COLD);
        tag(TwuBiomeTags.SPAWNS_WARM_VARIANT_MUDCRABS)
                .addTag(Tags.Biomes.IS_HOT);
    }
}
