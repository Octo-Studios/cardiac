package it.hurts.sskirillss.cardiac.level;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.hurts.sskirillss.cardiac.init.CodecRegistry;
import it.hurts.sskirillss.cardiac.init.ItemRegistry;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public class LifeBottleLootModifier extends LootModifier {
    public static final Codec<LifeBottleLootModifier> CODEC = RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, LifeBottleLootModifier::new));

    public LifeBottleLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource random = context.getRandom();

        if (context.getQueriedLootTableId().getPath().matches(".*chests.*") && random.nextFloat() <= 0.25F)
            generatedLoot.add(new ItemStack(ItemRegistry.LIFE_BOTTLE.get(), 1 + random.nextInt(3)));

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CodecRegistry.LIFE_BOTTLE.get();
    }
}