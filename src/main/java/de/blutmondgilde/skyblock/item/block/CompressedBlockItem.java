package de.blutmondgilde.skyblock.item.block;

import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class CompressedBlockItem extends BlockItem {
    public CompressedBlockItem(Block pBlock) {
        super(pBlock, new Properties().tab(SkyblockTabs.GENERAL));
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
