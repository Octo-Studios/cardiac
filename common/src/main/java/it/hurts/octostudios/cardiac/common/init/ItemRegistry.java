package it.hurts.octostudios.cardiac.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.hurts.octostudios.cardiac.common.Cardiac;
import it.hurts.octostudios.cardiac.common.items.LifeBottleItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Cardiac.MODID, Registries.ITEM);

    public static final RegistrySupplier<Item> LIFE_BOTTLE = ITEMS.register("life_bottle", LifeBottleItem::new);

    public static void registerCommon() {
        ITEMS.register();
    }
}