package it.hurts.octostudios.cardiac.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.hurts.octostudios.cardiac.common.Cardiac;
import it.hurts.octostudios.cardiac.common.enchantments.LifestealEnchantment;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class EnchantmentRegistry {
    private static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Cardiac.MODID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<Enchantment> LIFESTEAL = ENCHANTMENTS.register("lifesteal", LifestealEnchantment::new);

    public static void registerCommon() {
        ENCHANTMENTS.register();
    }
}