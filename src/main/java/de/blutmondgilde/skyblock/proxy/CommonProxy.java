package de.blutmondgilde.skyblock.proxy;

import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.event.MinionEventHandler;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public abstract class CommonProxy {
    protected final IEventBus modBus, forgeBus;

    public CommonProxy() {
        modBus = FMLJavaModLoadingContext.get().getModEventBus();
        forgeBus = MinecraftForge.EVENT_BUS;

        modBus.addListener(this::setup);
        modBus.addListener(this::clientSetup);
        modBus.addListener(this::addEntityAttributes);
        SkyblockRegistries.init(modBus);
        MinionEventHandler.init(forgeBus);
    }

    /**
     * Simple check if the current instance is a Client or a Server
     */
    public abstract boolean isClient();

    protected void setup(final FMLCommonSetupEvent e) {}

    protected void clientSetup(final FMLClientSetupEvent e) {}

    protected void addEntityAttributes(EntityAttributeCreationEvent e) {
        e.put(SkyblockRegistries.entities.coalMiner.get(), MinerEntity.setCustomAttributes().build());
    }
}
