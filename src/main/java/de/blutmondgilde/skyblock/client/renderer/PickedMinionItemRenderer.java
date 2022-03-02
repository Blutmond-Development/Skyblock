package de.blutmondgilde.skyblock.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import de.blutmondgilde.skyblock.client.model.PickedFarmerItemModel;
import de.blutmondgilde.skyblock.client.model.PickedMinerItemModel;
import de.blutmondgilde.skyblock.item.PickedMinionItem;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PickedMinionItemRenderer extends GeoItemRenderer<PickedMinionItem> {
    public PickedMinionItemRenderer() {
        super(new PickedMinerItemModel());
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType p_239207_2_, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        if (itemStack.hasTag()) {
            if (itemStack.getTag().contains("minion")) {
                String type = itemStack.getTag().getString("minion");
                type = type.substring(type.lastIndexOf('_') + 1);
                modelProvider = switch (type) {
                    case "miner" -> new PickedMinerItemModel();
                    case "farmer" -> new PickedFarmerItemModel();
                    default -> modelProvider;
                };
            }
        }

        super.renderByItem(itemStack, p_239207_2_, matrixStack, bufferIn, combinedLightIn, p_239207_6_);
    }
}
