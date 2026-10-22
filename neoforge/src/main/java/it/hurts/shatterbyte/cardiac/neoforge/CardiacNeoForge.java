package it.hurts.shatterbyte.cardiac.neoforge;

import it.hurts.shatterbyte.cardiac.common.Cardiac;
import it.hurts.shatterbyte.cardiac.common.init.EntityRegistry;
import it.hurts.shatterbyte.cardiac.common.init.ItemRegistry;
import it.hurts.shatterbyte.cardiac.common.init.SoundRegistry;
import it.hurts.shatterbyte.cardiac.common.registry.DeferredRegistry;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(Cardiac.MODID)
public final class CardiacNeoForge {
    public CardiacNeoForge(IEventBus modBus) {
        Cardiac.init();

        modBus.addListener(CardiacNeoForge::onRegister);
        modBus.addListener(CardiacNeoForge::onCommonSetup);
        modBus.addListener(CardiacNeoForge::onBuildCreativeModeTabContents);

        NeoForge.EVENT_BUS.addListener(CardiacNeoForge::onLivingDeath);
        NeoForge.EVENT_BUS.addListener(CardiacNeoForge::onLootTableLoad);

        if (FMLEnvironment.getDist() == Dist.CLIENT)
            new CardiacNeoForgeClient(modBus);
    }

    private static void onRegister(RegisterEvent event) {
        register(event, SoundRegistry.SOUNDS);
        register(event, EntityRegistry.ENTITIES);
        register(event, ItemRegistry.ITEMS);
    }

    private static <T> void register(RegisterEvent event, DeferredRegistry<T> registry) {
        event.register(registry.getRegistryKey(), helper -> registry.registerAll(helper::register));
    }

    private static void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Cardiac::setup);
    }

    private static void onBuildCreativeModeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
            event.accept(ItemRegistry.LIFE_BOTTLE.get());
    }

    private static void onLivingDeath(LivingDeathEvent event) {
        Cardiac.onLivingDeath(event.getEntity(), event.getSource());
    }

    private static void onLootTableLoad(LootTableLoadEvent event) {
        Cardiac.onModifyLootTable(event.getKey(), pool -> event.getTable().addPool(pool.build()));
    }
}
