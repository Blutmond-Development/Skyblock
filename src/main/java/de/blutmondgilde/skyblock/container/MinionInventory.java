package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.fuel.MinionFuel;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MinionInventory extends ItemStackHandler {
    private final List<MinionInventoryListener> inventoryListeners = new ArrayList<>();

    public MinionInventory() {
        super(4);
    }

    public MinionInventory(int inventorySize) {
        super(4 + inventorySize);
    }

    public void addListener(MinionInventoryListener listener) {
        inventoryListeners.add(listener);
    }

    public void removeListener(MinionInventoryListener listener) {
        inventoryListeners.remove(listener);
    }

    public void setNewSize(int inventorySize) {
        //Get current Items
        NonNullList<ItemStack> items = stacks;
        //Expand Size
        super.setSize(inventorySize + 4);
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

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        inventoryListeners.forEach(listener -> listener.onInventoryChanged(this));
    }

    public boolean isFull() {
        for (int i = 4; i < getSlots(); i++) {
            ItemStack stackInSlot = getStackInSlot(i);
            if (stackInSlot.isEmpty()) return false;
            int maxStackSize = getSlotLimit(i);
            if (stackInSlot.getCount() < maxStackSize) return false;
        }
        return true;
    }

    public boolean hasFuelItem() {
        return !getFuelSlotStack().isEmpty();
    }

    public ItemStack getFuelSlotStack() {
        return getStackInSlot(0);
    }

    public void consumeFuel() {
        if(SkyblockRegistries.minionFuel.findByItem(this.getFuelSlotStack().getItem()).isConsumable()){
            setStackInSlot(0, ItemStack.EMPTY);
        }
    }
}
