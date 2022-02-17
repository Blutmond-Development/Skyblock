package de.blutmondgilde.skyblock.client.model;

import de.blutmondgilde.skyblock.entity.minion.farmer.FarmerEntity;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FarmerModel extends AnimatedGeoModel<FarmerEntity> {

    @Override
    public ResourceLocation getModelLocation(FarmerEntity object) {
        return new SkyblockResourceLocation("geo/farmer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FarmerEntity object) {
        return new SkyblockResourceLocation("textures/entity/farmer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FarmerEntity animatable) {
        return new SkyblockResourceLocation("animations/farmer.animation.json");
    }
}
