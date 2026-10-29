package it.hurts.shatterbyte.cardiac.common.config;

import it.hurts.shatterbyte.shatterlib.module.config.ConfigSide;
import it.hurts.shatterbyte.shatterlib.module.config.ShatterConfig;
import it.hurts.shatterbyte.shatterlib.module.config.type.annotation.Comment;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class CardiacConfig extends ShatterConfig {
    @Comment("Should life orbs drop when a mob is killed only by a player, or should whenever it dies")
    private boolean shouldBeKilledByPlayer = true;

    @Comment("Should life orbs be attracted to the player even if their health is already full")
    private boolean attractToFullHP = false;

    @Comment("Defines pairs where the key is either a mob's ID or a regex for matching mob IDs, and the value is the percentage of the slain entity's maximum health that will be stored in the life orbs")
    private Map<String, Double> generalPercentages = new LinkedHashMap<>() {{
        put(".*", 0.15D);
    }};

    @Comment("Defines pairs where the key is either a mob's ID or a regex for matching mob IDs, and the value is the extra percentage of the slain entity's maximum health added to the life orbs for each level of the Lifesteal enchantment")
    private Map<String, Double> lifestealPercentages = new LinkedHashMap<>() {{
        put(".*", 0.1D);
    }};

    @Comment("The minimum number of life orbs dropped from a slain entity. This does not affect the total amount of health they will collectively restore")
    private int minOrbsAmount = 2;

    @Comment("The maximum amount of health carried by a life orb")
    private double maxOrbHealth = 10D;

    @Comment("Time in seconds before the removal of the life orb")
    private int orbLifetime = 60;

    @Comment("The maximum distance in blocks from which a life orb can be attracted to the nearest player")
    private int orbFollowDistance = 8;

    @Override
    public String getName() {
        return "cardiac";
    }

    @Override
    public ConfigSide getSide() {
        return ConfigSide.COMMON;
    }

    @Override
    public int getSchemaVersion() {
        return 1;
    }
}
