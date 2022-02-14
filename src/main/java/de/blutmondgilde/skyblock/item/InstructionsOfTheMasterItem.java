package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.Item;

public class InstructionsOfTheMasterItem extends Item {
    public InstructionsOfTheMasterItem() {
        super(new Properties().tab(SkyblockTabs.GENERAL).stacksTo(1));
    }
}
