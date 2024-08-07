package it.hurts.octostudios.cardiac.common.init;

import it.hurts.octostudios.cardiac.common.config.CardiacConfig;
import it.hurts.octostudios.octolib.modules.config.ConfigManager;

public class ConfigRegistry {
    public static CardiacConfig CONFIG = new CardiacConfig();

    public static void registerCommon() {
        ConfigManager.registerConfig("cardiac", CONFIG);
    }
}