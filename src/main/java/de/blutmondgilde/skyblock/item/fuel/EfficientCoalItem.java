package de.blutmondgilde.skyblock.item.fuel;

import de.blutmondgilde.skyblock.fuel.MinionFuelItem;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EfficientCoalItem extends Item implements MinionFuelItem {
    private static final long duration = 24 * 60 * 60 * 20;

    public EfficientCoalItem() {
        super((new Properties()).tab(SkyblockTabs.MINION_UPGRADES));
    }

    @Override
    public long getDurationTicks() {
        return duration;
    }

    @Override
    public float getEfficiency(ItemStack stack, Level level) {
        return 0.10F;
    }
}
