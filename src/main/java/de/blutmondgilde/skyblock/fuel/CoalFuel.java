package de.blutmondgilde.skyblock.fuel;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CoalFuel extends MinionFuel {
    private static final long durationTicks = 30 * 60 * 20;

    public CoalFuel() {
        super(() -> Items.COAL);
    }

    @Override
    public long getDurationTicks() {
        return durationTicks;
    }

    @Override
    public float getEfficiencyBoost(ItemStack stack, Level level) {
        return 0.05F;
    }
}
