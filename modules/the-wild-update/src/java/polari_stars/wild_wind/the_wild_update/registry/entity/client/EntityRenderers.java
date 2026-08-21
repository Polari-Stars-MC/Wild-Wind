package polari_stars.wild_wind.the_wild_update.registry.entity.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.client.renderer.MudcrabRenderer;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;

@EventBusSubscriber(modid = Twu.MODID, value = Dist.CLIENT)
public class EntityRenderers {
    @SubscribeEvent
    public static void registry(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(TwuEntityTypes.MUDCRAB.get(), MudcrabRenderer::new);
    }
}