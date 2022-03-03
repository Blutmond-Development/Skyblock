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
        registerMinerAttribute(e, SkyblockRegistries.entities.coalMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.cobblestoneMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.copperMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.diamondMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.emeraldMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.endStoneMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.glowstoneMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.goldMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.gravelMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.hardStoneMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.iceMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.ironMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.lapisMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.mithrilMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.obsidianMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.quartzMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.redstoneMiner);
        registerMinerAttribute(e, SkyblockRegistries.entities.sandMiner);

        registerFarmerAttribute(e, SkyblockRegistries.entities.wheatFarmer);
        registerFarmerAttribute(e, SkyblockRegistries.entities.carrotFarmer);
        registerFarmerAttribute(e, SkyblockRegistries.entities.potatoFarmer);
        registerFarmerAttribute(e, SkyblockRegistries.entities.beetrootFarmer);
    }

    private <T extends MinionEntity> void registerMinerAttribute(EntityAttributeCreationEvent e, RegistryObject<EntityType<T>> entry) {
        e.put(entry.get(), MinerEntity.setCustomAttributes().build());
    }

    private <T extends MinionEntity> void registerFarmerAttribute(EntityAttributeCreationEvent e, RegistryObject<EntityType<T>> entry) {
        e.put(entry.get(), MinerEntity.setCustomAttributes().build());
    }
}
