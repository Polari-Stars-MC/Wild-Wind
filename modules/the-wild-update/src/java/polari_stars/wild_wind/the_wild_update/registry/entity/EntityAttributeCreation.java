package polari_stars.wild_wind.the_wild_update.registry.entity;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.Mudcrab;

@EventBusSubscriber(modid = Twu.MODID)
public class EntityAttributeCreation {
    @SubscribeEvent
    public static void registry(EntityAttributeCreationEvent event) {
        event.put(TwuEntityTypes.MUDCRAB.get(), Mudcrab.createAttributes().build());
    }

    @SubscribeEvent
    public void onGeoReplacedEntityPreRender(EntityRenderersEvent.RegisterRenderers event) {
    }

}
