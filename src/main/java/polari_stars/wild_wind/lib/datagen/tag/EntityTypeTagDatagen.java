package polari_stars.wild_wind.lib.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import polari_stars.wild_wind.lib.WildWindLib;

import java.util.concurrent.CompletableFuture;

public class EntityTypeTagDatagen extends EntityTypeTagsProvider {
    public EntityTypeTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, WildWindLib.MODID);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
    }
}
