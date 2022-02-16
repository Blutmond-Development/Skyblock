package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class NoEffectBlockItem extends BlockItem {
    public NoEffectBlockItem(RegistryObject<? extends Block> parent) {
        super(parent.get(), new Properties().tab(SkyblockTabs.GENERAL));
    }
}
