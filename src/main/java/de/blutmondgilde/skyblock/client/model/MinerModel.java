package de.blutmondgilde.skyblock.client.model;

import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MinerModel extends AnimatedGeoModel<MinerEntity> {

    @Override
    public ResourceLocation getModelLocation(MinerEntity object) {
        return new SkyblockResourceLocation("geo/miner.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(MinerEntity object) {
        return new SkyblockResourceLocation("textures/entity/miner.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(MinerEntity animatable) {
        return new SkyblockResourceLocation("animations/miner.animation.json");
    }
}
