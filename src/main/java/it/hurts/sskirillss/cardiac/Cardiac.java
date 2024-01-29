package it.hurts.sskirillss.cardiac;

import it.hurts.sskirillss.cardiac.init.EntityRegistry;
import it.hurts.sskirillss.cardiac.init.ItemRegistry;
import it.hurts.sskirillss.cardiac.init.SoundRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Cardiac.MODID)
public class Cardiac {
    public static final String MODID = "cardiac";

    public Cardiac() {
        MinecraftForge.EVENT_BUS.register(this);

        EntityRegistry.register();
        SoundRegistry.register();
        ItemRegistry.register();
    }
}