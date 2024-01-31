package it.hurts.sskirillss.cardiac.init;

import com.mojang.serialization.Codec;
import it.hurts.sskirillss.cardiac.Cardiac;
import it.hurts.sskirillss.cardiac.level.LifeBottleLootModifier;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CodecRegistry {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> CODECS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Cardiac.MODID);

    public static final RegistryObject<Codec<LifeBottleLootModifier>> LIFE_BOTTLE = CODECS.register("life_bottle", () -> LifeBottleLootModifier.CODEC);

    public static void register() {
        CODECS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}