package it.hurts.sskirillss.cardiac.level;

import com.google.gson.JsonObject;
import it.hurts.sskirillss.cardiac.Cardiac;
import it.hurts.sskirillss.cardiac.config.CardiacConfig;
import it.hurts.sskirillss.cardiac.init.ItemRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class LifeBottleLootModifier extends LootModifier {
    public LifeBottleLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected @NotNull List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (!CardiacConfig.LIFE_BOTTLE_GEN_ENABLED.get())
            return generatedLoot;

        Random random = context.getRandom();

        if (context.getQueriedLootTableId().getPath().matches(".*chests.*") && random.nextDouble() <= CardiacConfig.LIFE_BOTTLE_GEN_CHANCE.get())
            generatedLoot.add(new ItemStack(ItemRegistry.LIFE_BOTTLE.get(), Math.max(1, random.nextInt(CardiacConfig.LIFE_BOTTLE_GEN_MAX_AMOUNT.get()))));

        return generatedLoot;
    }

    private static class Serializer extends GlobalLootModifierSerializer<LifeBottleLootModifier> {
        @Override
        public LifeBottleLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new LifeBottleLootModifier(conditions);
        }

        @Override
        public JsonObject write(LifeBottleLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }

    @Mod.EventBusSubscriber(modid = Cardiac.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandler {
        @SubscribeEvent
        public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
            event.getRegistry().register(new LifeBottleLootModifier.Serializer().setRegistryName(
                    new ResourceLocation(Cardiac.MODID, "life_bottle")));
        }
    }
}