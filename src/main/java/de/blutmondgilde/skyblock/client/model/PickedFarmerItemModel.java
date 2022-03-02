package de.blutmondgilde.skyblock.client.model;

import de.blutmondgilde.skyblock.item.PickedMinionItem;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PickedFarmerItemModel extends AnimatedGeoModel<PickedMinionItem> {
    @Override
    public ResourceLocation getModelLocation(PickedMinionItem object) {
        return new SkyblockResourceLocation("geo/item.farmer.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(PickedMinionItem object) {
        return new SkyblockResourceLocation("textures/entity/farmer.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(PickedMinionItem animatable) {
        return new SkyblockResourceLocation("animations/item.farmer.animation.json");
    }
}
