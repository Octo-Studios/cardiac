package it.hurts.shatterbyte.cardiac.common.init;

import it.hurts.shatterbyte.cardiac.common.entities.LifeOrb;
import it.hurts.shatterbyte.cardiac.common.entities.ThrownLifeBottle;
import it.hurts.shatterbyte.cardiac.common.registry.DeferredRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class EntityRegistry {
    public static final DeferredRegistry<EntityType<?>> ENTITIES = DeferredRegistry.create(Registries.ENTITY_TYPE);

    public static final Supplier<EntityType<LifeOrb>> LIFE_ORB = ENTITIES.register("life_orb", key ->
            EntityType.Builder.of(LifeOrb::new, MobCategory.MISC)
                    .sized(0.3F, 0.3F)
                    .fireImmune()
                    .build(key)
    );

    public static final Supplier<EntityType<ThrownLifeBottle>> THROWN_LIFE_BOTTLE = ENTITIES.register("thrown_life_bottle", key ->
            EntityType.Builder.<ThrownLifeBottle>of(ThrownLifeBottle::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .fireImmune()
                    .build(key)
    );
}
