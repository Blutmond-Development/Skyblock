package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.fuel.MinionFuel;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class MinerMinionInventory extends ItemStackHandler {
    public MinerMinionInventory() {
        super(4);
    }

    public MinerMinionInventory(int inventorySize) {
        super(4 + inventorySize);
    }

    @Override
    public void setSize(int size) {
        //Get current Items
        NonNullList<ItemStack> items = stacks;
        //Expand Size
        super.setSize(size);
        //Insert old items into their old slots
        for (int i = 0; i < items.size(); i++) {
            stacks.set(i, items.get(i));
        }
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        //check minion food slot
        if (slot == 0) {
            Optional<MinionFuel> fuel = Optional.ofNullable(SkyblockRegistries.minionFuel.findByItem(stack.getItem()));
            return fuel.isPresent();
        }

        if (slot == 1) {
            //check minion special upgrade slot
            return stack.is(SkyblockTags.MINION_SPECIAL_UPGRADES);
        }

        if (slot == 2 || slot == 3) {
            //check minion upgrade slot
            return stack.is(SkyblockTags.MINION_UPGRADES);
        }

        return super.isItemValid(slot, stack);
    }

    @Override
    protected int getStackLimit(int slot, @NotNull ItemStack stack) {
        if (slot <= 3) {
            return 1;
        }

        return super.getStackLimit(slot, stack);
    }
}
