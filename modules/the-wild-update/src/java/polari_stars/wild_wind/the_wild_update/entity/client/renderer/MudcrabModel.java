package polari_stars.wild_wind.the_wild_update.entity.client.renderer;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.google.common.collect.Comparators;
import net.minecraft.resources.Identifier;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.entity.Mudcrab;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class MudcrabModel extends DefaultedEntityGeoModel<Mudcrab> {
    public static final Map<Mudcrab.Variant, Identifier> TEXTURE_MAP;

    static {
        HashMap<Mudcrab.Variant, Identifier> objectObjectHashMap = new HashMap<>();
        for (Mudcrab.Variant value : Mudcrab.Variant.values()) {
            objectObjectHashMap.put(value, Twu.namespace("textures/entity/mudcrab_" + value.getName() + ".png"));
        }
        TEXTURE_MAP = Collections.unmodifiableMap(objectObjectHashMap);
    }

    public MudcrabModel() {
        super(Twu.namespace("mudcrab"));
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return renderState instanceof MudcrabRenderer.RenderState renderState1 ?
                TEXTURE_MAP.get(renderState1.variant) : TEXTURE_MAP.get(Mudcrab.Variant.DEFAULT);
    }
}
