package de.blutmondgilde.skyblock.fuel;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class CoalBlockFuel extends MinionFuel {
    private static final long durationTicks = 5 * 60 * 60 * 20;

    public CoalBlockFuel() {
        super(() -> Items.COAL_BLOCK);
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
