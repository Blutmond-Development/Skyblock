package de.blutmondgilde.skyblock.client.model;

import de.blutmondgilde.skyblock.item.PickedMinionItem;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PickedMinerItemModel extends AnimatedGeoModel<PickedMinionItem> {
    @Override
    public ResourceLocation getModelLocation(PickedMinionItem object) {
        return new SkyblockResourceLocation("geo/item.miner.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PickedMinionItem object) {
        return new SkyblockResourceLocation("textures/entity/miner.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PickedMinionItem animatable) {
        return new SkyblockResourceLocation("animations/item.miner.animation.json");
    }
}
