package polari_stars.wild_wind.the_wild_update.registry.item;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import polari_stars.wild_wind.lib.item.WildWindMobBucketItem;
import polari_stars.wild_wind.lib.item.WildWindSpawnEggItem;
import polari_stars.wild_wind.lib.registry.LibItems;
import polari_stars.wild_wind.the_wild_update.item.OmniClaw;
import polari_stars.wild_wind.the_wild_update.Twu;
import polari_stars.wild_wind.the_wild_update.registry.entity.TwuEntityTypes;

public interface TwuItems {
    DeferredRegister.Items REGISTER = DeferredRegister.Items.createItems(Twu.MODID);

    DeferredItem<SpawnEggItem> MUDCRAB_SPAWN_EGG = LibItems.register(REGISTER, "mudcrab_spawn_egg", "Mudcrab Spawn Egg", "泥沼蟹刷怪蛋",
            (Item.Properties type) -> new WildWindSpawnEggItem(TwuEntityTypes.MUDCRAB::get, type));
    DeferredItem<WildWindMobBucketItem> MUDCRAB_BUCKET = LibItems.register(REGISTER, "mudcrab_bucket", "Bucket of Mudcrab", "泥沼蟹桶",
            p -> new WildWindMobBucketItem(TwuEntityTypes.MUDCRAB::get, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, p), p -> p
                    .stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY));
    DeferredItem<OmniClaw> OMNI_CLAW = LibItems.register(REGISTER, "omni_claw", "Omni Claw", "万用蟹钳",
            OmniClaw::new, p -> p
                    .stacksTo(1));
    DeferredItem<Item> CRAB_CLAW = LibItems.register(REGISTER, "crab_claw", "Crab Claw", "蟹钳",
            Item::new);

    static void init(IEventBus iEventBus) {
        REGISTER.register(iEventBus);
    }
}
