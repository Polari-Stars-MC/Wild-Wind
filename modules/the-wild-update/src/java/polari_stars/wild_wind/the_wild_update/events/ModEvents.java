package polari_stars.wild_wind.the_wild_update.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;
import polari_stars.wild_wind.lib.WildWindLib;
import polari_stars.wild_wind.the_wild_update.Twu;

@EventBusSubscriber(modid = Twu.MODID)
public class ModEvents {
    public static final Logger LOGGER = Twu.LOGGER;

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}
