package polari_stars.wild_wind.lib;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(WildWindLib.MODID)
public class WildWindLib {
    public static final String MODID = "wild_wind_lib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WildWindLib(IEventBus modEventBus, ModContainer modContainer) {
    }
}
