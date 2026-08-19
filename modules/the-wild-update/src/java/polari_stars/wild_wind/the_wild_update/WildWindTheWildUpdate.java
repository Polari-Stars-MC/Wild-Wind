package polari_stars.wild_wind.the_wild_update;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(WildWindTheWildUpdate.MODID)
public class WildWindTheWildUpdate {
    public static final String MODID = "wild_wind_the_wild_update";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WildWindTheWildUpdate(IEventBus modEventBus, ModContainer modContainer) {
    }
}

