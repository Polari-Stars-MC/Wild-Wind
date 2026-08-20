package polari_stars.wild_wind.lib.datagen;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ParticleDatagen extends ParticleDescriptionProvider {
    public ParticleDatagen(PackOutput output) {
        super(output);
    }

    @Override
    protected void addDescriptions() {
    }

    protected void spriteSet(DeferredHolder<ParticleType<?>, ParticleType<?>> type, int numOfTextures, boolean reverse) {
        spriteSet(type.get(), type.getId(), numOfTextures, reverse);
    }
}
