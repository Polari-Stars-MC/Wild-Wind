package polari_stars.wild_wind.lib;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = WildWindLib.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = WildWindLib.MODID, value = Dist.CLIENT)
public class WildWindLibClient {
    public WildWindLibClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
