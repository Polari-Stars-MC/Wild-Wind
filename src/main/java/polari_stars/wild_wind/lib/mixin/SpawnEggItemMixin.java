package polari_stars.wild_wind.lib.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import polari_stars.wild_wind.lib.item.WildWindSpawnEggItem;

@Mixin(SpawnEggItem.class)
public abstract class SpawnEggItemMixin {
    @Inject(method = "getType", at = @At("HEAD"), cancellable = true)
    private static void wild_wind$getType(ItemStack itemStack, CallbackInfoReturnable<EntityType<?>> cir) {
        if (itemStack.getItem() instanceof WildWindSpawnEggItem wildWindSpawnEggItem) {
            cir.setReturnValue(wildWindSpawnEggItem.getType());
        }
    }
}
