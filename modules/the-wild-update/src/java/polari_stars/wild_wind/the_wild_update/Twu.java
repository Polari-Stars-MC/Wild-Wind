package polari_stars.wild_wind.the_wild_update;

import net.minecraft.core.Registry;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuSensorTypes;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;
import polari_stars.wild_wind.the_wild_update.registry.item.TwuDataComponentTypes;
import polari_stars.wild_wind.the_wild_update.registry.item.TwuItems;
import polari_stars.wild_wind.the_wild_update.registry.tag.TwuTags;

@Mod(Twu.MODID)
public class Twu {
    public static final String MODID = "wild_wind_the_wild_update";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Twu(IEventBus modEventBus, ModContainer modContainer) {
        TwuTags.init();
        TwuSensorTypes.init(modEventBus);
        TwuDataComponentTypes.init(modEventBus);
        TwuEntityTypes.init(modEventBus);
        TwuItems.init(modEventBus);
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

