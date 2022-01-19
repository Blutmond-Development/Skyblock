package de.blutmondgilde.skyblock.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class CommonProxy {
    protected final IEventBus modBus, forgeBus;

    public CommonProxy() {
        modBus = FMLJavaModLoadingContext.get().getModEventBus();
        forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(this::setup);
    }

    /**
     * Simple check if the current instance is a Client or a Server
     */
    public abstract boolean isClient();

    private void setup(final FMLCommonSetupEvent e) {

    }
}
