package de.blutmondgilde.skyblock.item.fuel;

import de.blutmondgilde.skyblock.fuel.MinionFuelItem;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EfficientCharCoalItem extends Item implements MinionFuelItem {
    private static final long duration = 36 * 60 * 60 * 20;

    public EfficientCharCoalItem() {
        super((new Properties()).tab(SkyblockTabs.GENERAL));
    }

    @Override
    public long getDurationTicks() {
        return duration;
    }

    @Override
    public float getEfficiency(ItemStack stack, Level level) {
        return 0.20F;
    }
}
