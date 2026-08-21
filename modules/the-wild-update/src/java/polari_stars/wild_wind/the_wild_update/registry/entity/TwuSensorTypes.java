package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.ai.sensing.MudcrabAttackablesSensor;

import java.util.function.Supplier;

public interface TwuSensorTypes {
    DeferredRegister<SensorType<?>> REGISTER = Twu.register(BuiltInRegistries.SENSOR_TYPE);

    DeferredHolder<SensorType<?>, SensorType<MudcrabAttackablesSensor>> MUDCRAB_ATTACKABLES = register("mudcrab_attackables", MudcrabAttackablesSensor::new);

    static void init(IEventBus iEventBus) {
        REGISTER.register(iEventBus);
    }

    private static <U extends Sensor<?>> DeferredHolder<SensorType<?>, SensorType<U>> register(String name, Supplier<U> factory) {
        return REGISTER.register(name, () -> new SensorType<>(factory));
    }
}
