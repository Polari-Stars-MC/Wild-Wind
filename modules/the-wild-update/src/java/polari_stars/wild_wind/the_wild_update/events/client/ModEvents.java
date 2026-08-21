package polari_stars.wild_wind.the_wild_update.events.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;
import polari_stars.wild_wind.lib.WildWindLib;
import polari_stars.wild_wind.lib.mixin.accessor.CreativeModeTabAccessor;
import polari_stars.wild_wind.lib.registry.LibCreativeModeTabs;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.registry.item.TwuItems;

@EventBusSubscriber(modid = Twu.MODID, value = Dist.CLIENT)
public class ModEvents {
    public static final Logger LOGGER = Twu.LOGGER;

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        LOGGER.info("HELLO FROM CLIENT SETUP");
    }

    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == LibCreativeModeTabs.WILD_WIND.getKey()) {
            ((CreativeModeTabAccessor) event.getTab()).wild_wind$setIconItemStack(TwuItems.OMNI_CLAW.toStack());
            LibCreativeModeTabs.addRegistryItem(TwuItems.REGISTER, event);
        }
    }
}
