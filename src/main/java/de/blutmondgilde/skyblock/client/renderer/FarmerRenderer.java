package de.blutmondgilde.skyblock.client.renderer;

import de.blutmondgilde.skyblock.client.model.FarmerModel;
import de.blutmondgilde.skyblock.entity.minion.farmer.FarmerEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FarmerRenderer extends GeoEntityRenderer<FarmerEntity> {
    public FarmerRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FarmerModel());
        this.shadowRadius = 0.5F;
    }
}
