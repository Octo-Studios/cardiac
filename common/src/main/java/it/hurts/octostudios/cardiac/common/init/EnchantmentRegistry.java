package it.hurts.octostudios.cardiac.common.init;

import it.hurts.octostudios.cardiac.common.Cardiac;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRegistry {
    public static final ResourceKey<Enchantment> LIFESTEAL = key("lifesteal");

    private static ResourceKey<Enchantment> key(String key) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(Cardiac.MODID, key));
    }
}