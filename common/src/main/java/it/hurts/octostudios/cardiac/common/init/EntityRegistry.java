package it.hurts.octostudios.cardiac.common.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import it.hurts.octostudios.cardiac.common.Cardiac;
import it.hurts.octostudios.cardiac.common.entities.LifeOrb;
import it.hurts.octostudios.cardiac.common.entities.ThrownLifeBottle;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Cardiac.MODID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<LifeOrb>> LIFE_ORB = ENTITIES.register("life_orb", () ->
            EntityType.Builder.of(LifeOrb::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .fireImmune()
                    .build("life_orb")
    );

    public static final RegistrySupplier<EntityType<ThrownLifeBottle>> THROWN_LIFE_BOTTLE = ENTITIES.register("thrown_life_bottle", () ->
            EntityType.Builder.of(ThrownLifeBottle::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .fireImmune()
                    .build("thrown_life_bottle")
    );

    public static void registerCommon() {
        ENTITIES.register();
    }
}