package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import polari_stars.wild_wind.the_wild_update.Twu;

@EventBusSubscriber(modid = Twu.MODID)
public class RegisterSpawnPlacements {
    @SubscribeEvent
    public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(TwuEntityTypes.MUDCRAB.get(), SpawnPlacementTypes.MUDCRAB, Heightmap.Types.WORLD_SURFACE,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
    }
}
