package polari_stars.wild_wind.lib.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import polari_stars.wild_wind.lib.WildWindLib;

import java.util.concurrent.CompletableFuture;

public class DamageTypeTagDatagen extends DamageTypeTagsProvider {
    public DamageTypeTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, WildWindLib.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
    }
}
