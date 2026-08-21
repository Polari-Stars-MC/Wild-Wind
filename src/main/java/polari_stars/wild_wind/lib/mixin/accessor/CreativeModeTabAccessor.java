package polari_stars.wild_wind.lib.mixin.accessor;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CreativeModeTab.class)
public interface CreativeModeTabAccessor {
    @Accessor("iconItemStack")
    ItemStack wild_wind$getIconItemStack();

    @Accessor("iconItemStack")
    void wild_wind$setIconItemStack(ItemStack itemStack);
}
