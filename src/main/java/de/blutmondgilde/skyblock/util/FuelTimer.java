package de.blutmondgilde.skyblock.util;

import de.blutmondgilde.skyblock.fuel.MinionFuel;
import lombok.Getter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FuelTimer {
    @Getter
    private float currentModifier = 0F;
    private long remainingTicks = 0;

    public void tick() {
        if (remainingTicks > 0) {
            remainingTicks--;
            //Reset Stats
            if (remainingTicks == 0) {
                currentModifier = 0F;
            }
        }
    }

    /**
     * Updates the current fuel stats.
     *
     * @return true = consume ItemStack, false = don't consume ItemStack
     */
    public boolean setFuel(MinionFuel fuel, ItemStack stack, Level level) {
        if (remainingTicks == 0) {
            this.remainingTicks = fuel.getDurationTicks();
            this.currentModifier = fuel.getEfficiencyBoost(stack, level);
            return true;
        } else {
            return false;
        }
    }
}
