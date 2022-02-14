package de.blutmondgilde.skyblock.fuel;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class MinionFuelWrapper extends MinionFuel {
    private final MinionFuelItem fuelItem;

    MinionFuelWrapper(Supplier<Item> item) {
        super(item);
        this.fuelItem = (MinionFuelItem) (item.get());
    }

    @Override
    public long getDurationTicks() {
        return fuelItem.getDurationTicks();
    }

    @Override
    public float getEfficiencyBoost(ItemStack stack, Level level) {
        return fuelItem.getEfficiency(stack, level);
    }

    @Override
    public boolean isConsumable() {
        return fuelItem.isConsumable();
    }

    public static <T extends Item & MinionFuelItem> MinionFuelWrapper of(T item) {
        return new MinionFuelWrapper(() -> item);
    }
}
