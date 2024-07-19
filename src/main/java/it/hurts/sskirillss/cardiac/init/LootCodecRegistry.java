package it.hurts.sskirillss.cardiac.init;

import com.mojang.serialization.MapCodec;
import it.hurts.sskirillss.cardiac.Cardiac;
import it.hurts.sskirillss.cardiac.level.LifeBottleLootModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class LootCodecRegistry {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> CODECS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Cardiac.MODID);

    public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<LifeBottleLootModifier>> LIFE_BOTTLE = CODECS.register("life_bottle", LifeBottleLootModifier.CODEC);

    public static void register(IEventBus bus) {
        CODECS.register(bus);
    }
}