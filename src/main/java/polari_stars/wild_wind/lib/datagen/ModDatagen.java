package polari_stars.wild_wind.lib.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import polari_stars.wild_wind.lib.WildWindLib;
import polari_stars.wild_wind.lib.datagen.lang.BasicLangDatagen;
import polari_stars.wild_wind.lib.datagen.lang.LangHandler;
import polari_stars.wild_wind.lib.datagen.tag.DamageTypeTagDatagen;
import polari_stars.wild_wind.lib.datagen.tag.EntityTypeTagDatagen;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("UnusedReturnValue")
@EventBusSubscriber(modid = WildWindLib.MODID)
public class ModDatagen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        String modid = WildWindLib.MODID;
        build(event, (output) -> new BasicLangDatagen(output, modid, LangHandler.EN_US));
        build(event, (output) -> new BasicLangDatagen(output, modid, LangHandler.ZH_CN));
        build(event, ParticleDatagen::new);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Server event) {
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        build(event, new EntityTypeTagDatagen(packOutput, lookupProvider));
        build(event, new DamageTypeTagDatagen(packOutput, lookupProvider));
    }

    public static <T extends DataProvider> T build(GatherDataEvent event, DataProvider provider) {
        return event.getGenerator().addProvider(true, (T) provider);
    }

    public static <T extends DataProvider> T build(GatherDataEvent event, DataProvider.Factory<T> provider) {
        return event.getGenerator().addProvider(true, provider);
    }
}
