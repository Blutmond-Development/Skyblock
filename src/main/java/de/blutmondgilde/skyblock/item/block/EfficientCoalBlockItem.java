package de.blutmondgilde.skyblock.item.block;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.BlockItem;

public class EfficientCoalBlockItem extends BlockItem {
    public EfficientCoalBlockItem() {
        super(SkyblockRegistries.blocks.efficientCoalBlock.get(), new Properties().tab(SkyblockTabs.MINION_UPGRADES));
    }
}
