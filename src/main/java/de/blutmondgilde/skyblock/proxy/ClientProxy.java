package de.blutmondgilde.skyblock.proxy;

import de.blutmondgilde.skyblock.client.renderer.MinerRenderer;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.EntityRenderersEvent;

public class ClientProxy extends CommonProxy {
    public ClientProxy() {
        super();
        modBus.addListener(this::registerEntityRenderers);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    private void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers e) {
        e.registerEntityRenderer(SkyblockRegistries.entities.coalMiner.get(), MinerRenderer::new);
    }

    @Override
    public Level getLevel() {
        return Minecraft.getInstance().level;
    }
}
