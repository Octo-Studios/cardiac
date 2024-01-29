package it.hurts.sskirillss.cardiac.init;

import it.hurts.sskirillss.cardiac.Cardiac;
import it.hurts.sskirillss.cardiac.entities.LifeOrb;
import it.hurts.sskirillss.cardiac.entities.ThrownLifeBottle;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Cardiac.MODID);

    public static final RegistryObject<EntityType<LifeOrb>> LIFE_ORB = ENTITIES.register("life_orb", () ->
            EntityType.Builder.of(LifeOrb::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .build("life_orb")
    );

    public static final RegistryObject<EntityType<ThrownLifeBottle>> THROWN_LIFE_BOTTLE = ENTITIES.register("thrown_life_bottle", () ->
            EntityType.Builder.of(ThrownLifeBottle::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .build("thrown_life_bottle")
    );

    public static void register() {
        ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}