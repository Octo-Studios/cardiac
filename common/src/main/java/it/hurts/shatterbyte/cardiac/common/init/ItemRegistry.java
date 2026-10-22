package it.hurts.shatterbyte.cardiac.common.init;

import it.hurts.shatterbyte.cardiac.common.items.LifeBottleItem;
import it.hurts.shatterbyte.cardiac.common.registry.DeferredRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegistry<Item> ITEMS = DeferredRegistry.create(Registries.ITEM);

    public static final Supplier<LifeBottleItem> LIFE_BOTTLE = ITEMS.register("life_bottle", key -> new LifeBottleItem(new Item.Properties().setId(key).rarity(Rarity.UNCOMMON)));
}
