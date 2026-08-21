package polari_stars.wild_wind.the_wild_update.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import polari_stars.wild_wind.lib.datagen.ParticleDatagen;
import polari_stars.wild_wind.lib.datagen.lang.BasicLangDatagen;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;
import polari_stars.wild_wind.the_wild_update.datagen.tag.BiomeTagDatagen;
import polari_stars.wild_wind.the_wild_update.datagen.tag.DamageTypeTagDatagen;
import polari_stars.wild_wind.the_wild_update.datagen.tag.EntityTypeTagDatagen;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.registry.TwuRegistrySets;

import java.util.concurrent.CompletableFuture;

import static polari_stars.wild_wind.lib.datagen.ModDatagen.build;

@EventBusSubscriber(modid = Twu.MODID)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        String modid = Twu.MODID;
        build(event, output -> new BasicLangDatagen(output, modid, LangHandler.EN_US));
        build(event, output -> new BasicLangDatagen(output, modid, LangHandler.ZH_CN));
        build(event, ParticleDatagen::new);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        event.createDatapackRegistryObjects(TwuRegistrySets.BUILDER);
        build(event, new BiomeTagDatagen(packOutput, lookupProvider));
        build(event, new EntityTypeTagDatagen(packOutput, lookupProvider));
        build(event, new DamageTypeTagDatagen(packOutput, lookupProvider));
    }
}
