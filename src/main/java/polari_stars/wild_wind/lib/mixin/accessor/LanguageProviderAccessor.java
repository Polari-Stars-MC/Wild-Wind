package polari_stars.wild_wind.lib.mixin.accessor;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LanguageProvider.class)
public interface LanguageProviderAccessor {
    @Accessor("locale")
    String wild_wind$getLocale();

    @Accessor("output")
    PackOutput wild_wind$getOutput();
}
