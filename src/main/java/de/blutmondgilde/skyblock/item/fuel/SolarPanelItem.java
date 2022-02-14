package de.blutmondgilde.skyblock.item.fuel;

import de.blutmondgilde.skyblock.fuel.MinionFuelItem;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SolarPanelItem extends Item implements MinionFuelItem {
    public SolarPanelItem() {
        super((new Item.Properties()).tab(SkyblockTabs.GENERAL));
    }

    @Override
    public long getDurationTicks() {
        return -1;
    }

    @Override
    public float getEfficiency(ItemStack stack, Level level) {
        return level.isDay() && !level.isRaining() ? 0.25F : 0F;
    }
}
