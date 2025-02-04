package it.hurts.octostudios.cardiac.common.config;

import it.hurts.octostudios.octolib.modules.config.annotations.Prop;
import it.hurts.octostudios.octolib.modules.config.impl.OctoConfig;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class CardiacConfig implements OctoConfig {
    @Prop(comment = "Should life orbs drop when a mob is killed only by a player, or should whenever it dies")
    private boolean shouldBeKilledByPlayer = true;

    @Prop(comment = "Should life orbs be attracted to the player even if their health is already full")
    private boolean attractToFullHP = true;

    @Prop(comment = "Defines pairs where the key is either a mob's ID or a regex for matching mob IDs, and the value is the percentage of the slain entity's maximum health that will be stored in the life orbs")
    private Map<String, Double> generalPercentages = new LinkedHashMap<>() {{
        put(".*", 0.15D);
    }};

    @Prop(comment = "Defines pairs where the key is either a mob's ID or a regex for matching mob IDs, and the value is the extra percentage of the slain entity's maximum health added to the life orbs for each level of the Lifesteal enchantment")
    private Map<String, Double> lifestealPercentages = new LinkedHashMap<>() {{
        put(".*", 0.1D);
    }};

    @Prop(comment = "The minimum number of life orbs dropped from a slain entity. This does not affect the total amount of health they will collectively restore")
    private int minOrbsAmount = 2;

    @Prop(comment = "The maximum amount of health carried by a life orb")
    private double maxOrbHealth = 10D;

    @Prop(comment = "Time in seconds before the removal of the life orb")
    private int orbLifetime = 60;

    @Prop(comment = "The maximum distance in blocks from which a life orb can be attracted to the nearest player")
    private int orbFollowDistance = 8;
}