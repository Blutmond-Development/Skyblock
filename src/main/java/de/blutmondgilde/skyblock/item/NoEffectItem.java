package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.Item;

public class NoEffectItem extends Item {
    public NoEffectItem() {
        super(new Properties().tab(SkyblockTabs.GENERAL));
    }
}
