package polari_stars.wild_wind.the_wild_update.entity.client.renderer;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jspecify.annotations.Nullable;
import polari_stars.wild_wind.the_wild_update.entity.Mudcrab;

public class MudcrabRenderer extends GeoEntityRenderer<Mudcrab, MudcrabRenderer.RenderState> {
    public MudcrabRenderer(EntityRendererProvider.Context context) {
        super(context, new MudcrabModel());
    }

    @Override
    public void addRenderData(Mudcrab animatable, @Nullable Void relatedObject, RenderState renderState, float partialTick) {
        renderState.variant = animatable.getVariant();
    }

    @Override
    public RenderState createRenderState(Mudcrab animatable, @Nullable Void relatedObject) {
        return new RenderState();
    }

    public static class RenderState extends EntityRenderState {
        public Mudcrab.Variant variant = Mudcrab.Variant.DEFAULT;
    }
}
