package it.hurts.shatterbyte.cardiac.common.init;

import it.hurts.shatterbyte.cardiac.common.Cardiac;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRegistry {
    public static final ResourceKey<Enchantment> LIFESTEAL = key("lifesteal");

    private static ResourceKey<Enchantment> key(String key) {
        return ResourceKey.create(Registries.ENCHANTMENT, Identifier.fromNamespaceAndPath(Cardiac.MODID, key));
    }
}
