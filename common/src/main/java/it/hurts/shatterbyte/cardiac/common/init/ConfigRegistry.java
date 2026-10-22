package it.hurts.shatterbyte.cardiac.common.init;

import it.hurts.shatterbyte.cardiac.common.config.CardiacConfig;
import it.hurts.shatterbyte.shatterlib.module.config.ConfigManager;
import it.hurts.shatterbyte.shatterlib.platform.ShatterLibServices;

import java.nio.file.Path;

public class ConfigRegistry {
    public static CardiacConfig CONFIG = new CardiacConfig();

    public static void registerCommon() {
        ConfigManager.register("cardiac", CONFIG);

        Path configDir;

        try {
            configDir = ShatterLibServices.platform().getConfigDirectory();
        } catch (IllegalStateException exception) {
            return;
        }

        CONFIG.load(configDir);
    }
}
