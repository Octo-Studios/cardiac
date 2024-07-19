package it.hurts.sskirillss.cardiac.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CardiacConfig {
    public static final ModConfigSpec GENERAL;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        setupConfig(builder);

        GENERAL = builder.build();
    }

    public static ModConfigSpec.BooleanValue SHOULD_BE_KILLED_BY_PLAYER;

    public static ModConfigSpec.DoubleValue GENERAL_PERCENTAGE;
    public static ModConfigSpec.DoubleValue LIFESTEAL_PERCENTAGE;

    public static ModConfigSpec.IntValue LIFE_ORBS_MIN_AMOUNT;

    public static ModConfigSpec.IntValue MAX_LIFESTEAL_LEVEL;

    public static ModConfigSpec.DoubleValue LIFE_ORB_MAX_HEALTH;
    public static ModConfigSpec.IntValue LIFE_ORB_LIFETIME;
    public static ModConfigSpec.IntValue LIFE_ORB_MAX_STAGES;
    public static ModConfigSpec.DoubleValue LIFE_ORB_FOLLOW_DISTANCE;

    public static ModConfigSpec.IntValue LIFE_BOTTLE_MIN_ORBS_AMOUNT;
    public static ModConfigSpec.IntValue LIFE_BOTTLE_MAX_ADDITIONAL_ORBS_AMOUNT;

    public static ModConfigSpec.DoubleValue LIFE_BOTTLE_MIN_LIFE_RESTORE;
    public static ModConfigSpec.DoubleValue LIFE_BOTTLE_MAX_ADDITIONAL_LIFE_RESTORE;

    public static ModConfigSpec.BooleanValue LIFE_BOTTLE_GEN_ENABLED;
    public static ModConfigSpec.DoubleValue LIFE_BOTTLE_GEN_CHANCE;
    public static ModConfigSpec.IntValue LIFE_BOTTLE_GEN_MAX_AMOUNT;

    private static void setupConfig(ModConfigSpec.Builder builder) {
        builder.push("General");

        SHOULD_BE_KILLED_BY_PLAYER = builder
                .comment("Life orbs can only be dropped from entities killed by the player")
                .define("playerOnly", false);

        GENERAL_PERCENTAGE = builder
                .comment("How much percent of the slain entity's maximum health will be placed in the life orbs")
                .defineInRange("generalPercentage", 0.15D, 0D, Double.MAX_VALUE);
        LIFESTEAL_PERCENTAGE = builder
                .comment("How much additional percent of the slain entity's maximum health will be placed in the life orbs per each level of Lifesteal enchantment")
                .defineInRange("lifestealPercentage", 0.1D, 0D, Double.MAX_VALUE);

        LIFE_ORBS_MIN_AMOUNT = builder
                .comment("Minimum number of life orbs dropped from slain entity. Does not affect the total amount of health they will collectively restore")
                .defineInRange("minLifeOrbs", 2, 0, Integer.MAX_VALUE);

        MAX_LIFESTEAL_LEVEL = builder
                .comment("The maximum allowed level of Lifesteal enchantment")
                .defineInRange("maxLifestealLevel", 2, 0, Integer.MAX_VALUE);

        LIFE_ORB_MAX_HEALTH = builder
                .comment("Maximum amount of health carried by a life orb")
                .defineInRange("lifeOrbMaxHealth", 10D, 0D, Double.MAX_VALUE);
        LIFE_ORB_LIFETIME = builder
                .comment("Time in seconds before removal of the life orb")
                .defineInRange("lifeOrbLifetime", 60, 0, Integer.MAX_VALUE);
        LIFE_ORB_MAX_STAGES = builder
                .comment("Maximum number of stages of the size of life orbs. Affects only the appearance and hitbox size. Requires additional textures when > 5")
                .defineInRange("lifeOrbMaxStages", 5, 1, Integer.MAX_VALUE);
        LIFE_ORB_FOLLOW_DISTANCE = builder
                .comment("Maximum distance to the nearest player to attract a life orb")
                .defineInRange("lifeOrbFollowDistance", 8D, 0D, Double.MAX_VALUE);

        LIFE_BOTTLE_MIN_ORBS_AMOUNT = builder
                .comment("Minimum number of life orbs falling out of the life bottle. Affects the total amount of health restored in the aggregate")
                .defineInRange("lifeBottleMinOrbs", 5, 0, Integer.MAX_VALUE);
        LIFE_BOTTLE_MAX_ADDITIONAL_ORBS_AMOUNT = builder
                .comment("Maximum number of additional life orbs dropped from the life bottle. Affects the total amount of health restored in the aggregate")
                .defineInRange("lifeBottleAdditionalOrbs", 10, 0, Integer.MAX_VALUE);

        LIFE_BOTTLE_MIN_LIFE_RESTORE = builder
                .comment("Minimum amount of health that can be replenished by a single life orb from a life bottle")
                .defineInRange("lifeBottleMinLifeRestore", 1D, 0D, Double.MAX_VALUE);
        LIFE_BOTTLE_MAX_ADDITIONAL_LIFE_RESTORE = builder
                .comment("Maximum amount of additional health that can be replenished by a single life orb from a life bottle")
                .defineInRange("lifeBottleAdditionalLifeRestore", 3D, 0D, Double.MAX_VALUE);

        LIFE_BOTTLE_GEN_ENABLED = builder
                .comment("Generating a life bottle in dungeon chests")
                .define("lifeBottleGen", true);
        LIFE_BOTTLE_GEN_CHANCE = builder
                .comment("The probability of generating a life bottle in dungeon chests: 0 = 0%, 1 = 100%")
                .defineInRange("lifeBottleGenChance", 0.25D, 0D, 1D);
        LIFE_BOTTLE_GEN_MAX_AMOUNT = builder
                .comment("Maximum number of life bottles that can be generated inside a dungeon chest")
                .defineInRange("lifeBottleGenMaxAmount", 3, 0, Integer.MAX_VALUE);

        builder.pop();
    }
}