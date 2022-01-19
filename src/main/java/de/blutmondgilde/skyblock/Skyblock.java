package de.blutmondgilde.skyblock;

import lombok.Getter;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Skyblock.MOD_ID)
public class Skyblock {
    public static final String MOD_ID = "skyblock";
    @Getter
    private static final Logger Logger = LogManager.getLogger();

    public Skyblock() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) {}
}
