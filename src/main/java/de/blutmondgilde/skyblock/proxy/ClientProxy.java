package de.blutmondgilde.skyblock.proxy;

import de.blutmondgilde.skyblock.client.event.handler.FuelToolTipHandler;
import de.blutmondgilde.skyblock.client.renderer.FarmerRenderer;
import de.blutmondgilde.skyblock.client.renderer.MinerRenderer;
import de.blutmondgilde.skyblock.client.screen.MinerMinionContainerScreen;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        super();
        modBus.addListener(this::registerEntityRenderers);
        forgeBus.addListener(FuelToolTipHandler::onRenderToolTip);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers e) {
        //Miner Entities
        e.registerEntityRenderer(SkyblockRegistries.entities.coalMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.cobblestoneMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.copperMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.diamondMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.emeraldMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.endStoneMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.glowstoneMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.goldMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.gravelMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.hardStoneMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.iceMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.ironMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.lapisMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.mithrilMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.obsidianMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.quartzMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.redstoneMiner.get(), MinerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.sandMiner.get(), MinerRenderer::new);
        //Farmer Entities
        e.registerEntityRenderer(SkyblockRegistries.entities.wheatFarmer.get(), FarmerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.carrotFarmer.get(), FarmerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.potatoFarmer.get(), FarmerRenderer::new);
        e.registerEntityRenderer(SkyblockRegistries.entities.beetrootFarmer.get(), FarmerRenderer::new);
    }

    @Override
    public Level getLevel() {
        return Minecraft.getInstance().level;
    }

    @Override
    protected void clientSetup(FMLClientSetupEvent e) {
        e.enqueueWork(() -> {
            MenuScreens.register(SkyblockRegistries.container.minerMinionMenu.get(), MinerMinionContainerScreen::new);
        });
    }
}
