package __MODULE_PACKAGE__;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(MODID)
public class __MODULE_CLASS__ {
    public static final String MODID = "__MODULE_ID__";
    public static final Logger LOGGER = LogUtils.getLogger();

    public __MODULE_CLASS__(IEventBus modEventBus, ModContainer modContainer) {
    }
}
