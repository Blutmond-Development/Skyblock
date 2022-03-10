package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.Skyblock;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class SkyblockTabs {
    public static final CreativeModeTab GENERAL = new CreativeModeTab(Skyblock.MOD_ID + ".minions") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(SkyblockRegistries.items.pickedMinion.get());
        }
    };
}
