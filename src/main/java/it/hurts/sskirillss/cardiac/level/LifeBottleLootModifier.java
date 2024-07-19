package it.hurts.sskirillss.cardiac.level;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.init.LootCodecRegistry;
import it.hurts.sskirillss.cardiac.init.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class LifeBottleLootModifier extends LootModifier {
    public static final Supplier<MapCodec<LifeBottleLootModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.mapCodec(inst -> codecStart(inst).apply(inst, LifeBottleLootModifier::new)));

    public LifeBottleLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (!CardiacConfig.LIFE_BOTTLE_GEN_ENABLED.get())
            return generatedLoot;

        RandomSource random = context.getRandom();

        if (context.getQueriedLootTableId().getPath().matches(".*chests.*") && random.nextDouble() <= CardiacConfig.LIFE_BOTTLE_GEN_CHANCE.get())
            generatedLoot.add(new ItemStack(ItemRegistry.LIFE_BOTTLE.get(), Math.max(1, random.nextInt(CardiacConfig.LIFE_BOTTLE_GEN_MAX_AMOUNT.get()))));

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return LootCodecRegistry.LIFE_BOTTLE.get();
    }
}