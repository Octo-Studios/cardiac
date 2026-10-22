package it.hurts.shatterbyte.cardiac.fabric;

import it.hurts.shatterbyte.cardiac.common.Cardiac;
import it.hurts.shatterbyte.cardiac.common.init.EntityRegistry;
import it.hurts.shatterbyte.cardiac.common.init.ItemRegistry;
import it.hurts.shatterbyte.cardiac.common.init.SoundRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;

public final class CardiacFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Cardiac.init();

        SoundRegistry.SOUNDS.registerAll((id, entry) -> Registry.register(BuiltInRegistries.SOUND_EVENT, id, entry));
        EntityRegistry.ENTITIES.registerAll((id, entry) -> Registry.register(BuiltInRegistries.ENTITY_TYPE, id, entry));
        ItemRegistry.ITEMS.registerAll((id, entry) -> Registry.register(BuiltInRegistries.ITEM, id, entry));

        Cardiac.setup();

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.INGREDIENTS).register(output -> output.accept(ItemRegistry.LIFE_BOTTLE.get()));

        ServerLivingEntityEvents.AFTER_DEATH.register(Cardiac::onLivingDeath);

        LootTableEvents.MODIFY.register((key, builder, source, registries) -> Cardiac.onModifyLootTable(key, builder::withPool));
    }
}
