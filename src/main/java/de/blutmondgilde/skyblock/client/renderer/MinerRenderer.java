package de.blutmondgilde.skyblock.client.renderer;

import de.blutmondgilde.skyblock.client.model.MinerModel;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class MinerRenderer extends GeoEntityRenderer<MinerEntity> {
    public MinerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MinerModel());
        this.shadowRadius = 0.5F;
    }
}
