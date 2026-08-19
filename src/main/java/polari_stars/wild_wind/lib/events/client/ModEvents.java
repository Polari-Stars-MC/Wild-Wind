package polari_stars.wild_wind.lib.events.client;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import polari_stars.wild_wind.lib.WildWindLib;

@EventBusSubscriber(modid = WildWindLib.MODID, value = Dist.CLIENT)
public class ModEvents {
    public static final Logger LOGGER = WildWindLib.LOGGER;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
        LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
