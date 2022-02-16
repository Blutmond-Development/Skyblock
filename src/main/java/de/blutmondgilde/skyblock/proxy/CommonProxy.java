package de.blutmondgilde.skyblock.proxy;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.event.handler.MinionEventHandler;
import de.blutmondgilde.skyblock.network.SkyblockNetwork;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;

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

    public Level getLevel() {
        return ServerLifecycleHooks.getCurrentServer().overworld();
    }

    protected void setup(final FMLCommonSetupEvent e) {
        e.enqueueWork(SkyblockNetwork::registerPackets);
    }

    protected void clientSetup(final FMLClientSetupEvent e) {}

    protected void addEntityAttributes(EntityAttributeCreationEvent e) {
        registerEntityAttribute(e, SkyblockRegistries.entities.coalMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.cobblestoneMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.copperMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.diamondMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.emeraldMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.endStoneMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.glowstoneMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.goldMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.gravelMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.hardStoneMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.iceMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.ironMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.lapisMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.mithrilMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.obsidianMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.quartzMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.redstoneMiner);
        registerEntityAttribute(e, SkyblockRegistries.entities.sandMiner);
    }

    private <T extends MinionEntity> void registerEntityAttribute(EntityAttributeCreationEvent e, RegistryObject<EntityType<T>> entry) {
        e.put(entry.get(), MinerEntity.setCustomAttributes().build());
    }
}
