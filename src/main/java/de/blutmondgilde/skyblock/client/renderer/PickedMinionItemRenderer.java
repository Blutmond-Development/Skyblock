package de.blutmondgilde.skyblock.client.renderer;

import de.blutmondgilde.skyblock.client.model.PickedMinionItemModel;
import de.blutmondgilde.skyblock.item.PickedMinionItem;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class PickedMinionItemRenderer extends GeoItemRenderer<PickedMinionItem> {
    public PickedMinionItemRenderer() {
        super(new PickedMinionItemModel());
    }
}
