package polari_stars.wild_wind.lib;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(WildWindLib.MODID)
public class WildWindLib {
    public static final String MODID = "wild_wind_lib";
    public static final Logger LOGGER = LogUtils.getLogger();

    public WildWindLib(IEventBus modEventBus, ModContainer modContainer) {
    }

    public static Identifier namespace(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    public static <T> DeferredRegister<T> register(Registry<T> registry) {
        return DeferredRegister.create(registry, MODID);
    }

    public static <T> DeferredRegister<T> register(ResourceKey<Registry<T>> registry) {
        return DeferredRegister.create(registry, MODID);
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(Identifier identifier) {
        return new CustomPacketPayload.Type<>(identifier);
    }

    public static <T extends CustomPacketPayload> CustomPacketPayload.Type<T> type(String identifier) {
        return type(Identifier.fromNamespaceAndPath(MODID, identifier));
    }
}
