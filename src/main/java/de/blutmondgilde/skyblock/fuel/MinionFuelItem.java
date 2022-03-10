package de.blutmondgilde.skyblock.fuel;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public interface MinionFuelItem {
    long getDurationTicks();

    float getEfficiency(ItemStack stack, Level level);

    default boolean isConsumable() {
        return true;
    }
}
