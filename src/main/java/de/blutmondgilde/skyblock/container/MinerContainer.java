package de.blutmondgilde.skyblock.container;

import com.google.common.collect.Lists;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class MinerContainer implements Container, Nameable, StackedContentsCompatible {
    private static final int size = 19;
    private final NonNullList<ItemStack> items = NonNullList.withSize(size, ItemStack.EMPTY);
    @Nullable
    private List<ContainerListener> listeners;
    private final TranslatableComponent title;

    public MinerContainer(MinerEntity miner) {
        this.title = new TranslatableComponent("skyblock.container.miner", miner.getCustomName(), miner.getMinionLevel());
    }

    public void addListener(ContainerListener pListener) {
        if (this.listeners == null) {
            this.listeners = Lists.newArrayList();
        }

        this.listeners.add(pListener);
    }

    /**
     * removes the specified IInvBasic from receiving further change notices
     */
    public void removeListener(ContainerListener pListener) {
        if (this.listeners != null) {
            this.listeners.remove(pListener);
        }

    }

    /**
     * Returns the stack in the given slot.
     */
    public ItemStack getItem(int pIndex) {
        return pIndex >= 0 && pIndex < this.items.size() ? this.items.get(pIndex) : ItemStack.EMPTY;
    }

    public List<ItemStack> removeAllItems() {
        List<ItemStack> list = this.items.stream().filter((p_19197_) -> {
            return !p_19197_.isEmpty();
        }).collect(Collectors.toList());
        this.clearContent();
        return list;
    }

    /**
     * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
     */
    public ItemStack removeItem(int pIndex, int pCount) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, pIndex, pCount);
        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    public ItemStack removeItemType(Item pItem, int pAmount) {
        ItemStack itemstack = new ItemStack(pItem, 0);

        for (int i = size - 1; i >= 0; --i) {
            ItemStack itemstack1 = this.getItem(i);
            if (itemstack1.getItem().equals(pItem)) {
                int j = pAmount - itemstack.getCount();
                ItemStack itemstack2 = itemstack1.split(j);
                itemstack.grow(itemstack2.getCount());
                if (itemstack.getCount() == pAmount) {
                    break;
                }
            }
        }

        if (!itemstack.isEmpty()) {
            this.setChanged();
        }

        return itemstack;
    }

    public ItemStack addItem(ItemStack pStack) {
        ItemStack itemstack = pStack.copy();
        this.moveItemToOccupiedSlotsWithSameType(itemstack);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.moveItemToEmptySlots(itemstack);
            return itemstack.isEmpty() ? ItemStack.EMPTY : itemstack;
        }
    }

    public boolean canAddItem(ItemStack pStack) {
        boolean flag = false;

        for (ItemStack itemstack : this.items) {
            if (itemstack.isEmpty() || ItemStack.isSameItemSameTags(itemstack, pStack) && itemstack.getCount() < itemstack.getMaxStackSize()) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public boolean hasSpace() {
        for (ItemStack itemStack : this.items) {
            if (itemStack.isEmpty()) return true;
            if (itemStack.getMaxStackSize() > itemStack.getCount()){
                return true;
            }
        }

        return false;
    }

    /**
     * Removes a stack from the given slot and returns it.
     */
    public ItemStack removeItemNoUpdate(int pIndex) {
        ItemStack itemstack = this.items.get(pIndex);
        if (itemstack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(pIndex, ItemStack.EMPTY);
            return itemstack;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    public void setItem(int pIndex, ItemStack pStack) {
        this.items.set(pIndex, pStack);
        if (!pStack.isEmpty() && pStack.getCount() > this.getMaxStackSize()) {
            pStack.setCount(this.getMaxStackSize());
        }

        this.setChanged();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    public int getContainerSize() {
        return size;
    }

    public boolean isEmpty() {
        for (ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    public void setChanged() {
        if (this.listeners != null) {
            for (ContainerListener containerlistener : this.listeners) {
                containerlistener.containerChanged(this);
            }
        }

    }

    /**
     * Don't rename this method to canInteractWith due to conflicts with Container
     */
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    public void clearContent() {
        this.items.clear();
        this.setChanged();
    }

    public void fillStackedContents(StackedContents pHelper) {
        for (ItemStack itemstack : this.items) {
            pHelper.accountStack(itemstack);
        }

    }

    public String toString() {
        return this.items.stream().filter((itemStack) -> !itemStack.isEmpty()).toList().toString();
    }

    private void moveItemToEmptySlots(ItemStack pStack) {
        for (int i = 0; i < size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (itemstack.isEmpty()) {
                this.setItem(i, pStack.copy());
                pStack.setCount(0);
                return;
            }
        }

    }

    private void moveItemToOccupiedSlotsWithSameType(ItemStack pStack) {
        for (int i = 0; i < size; ++i) {
            ItemStack itemstack = this.getItem(i);
            if (ItemStack.isSameItemSameTags(itemstack, pStack)) {
                this.moveItemsBetweenStacks(pStack, itemstack);
                if (pStack.isEmpty()) {
                    return;
                }
            }
        }

    }

    private void moveItemsBetweenStacks(ItemStack p_19186_, ItemStack p_19187_) {
        int i = Math.min(this.getMaxStackSize(), p_19187_.getMaxStackSize());
        int j = Math.min(p_19186_.getCount(), i - p_19187_.getCount());
        if (j > 0) {
            p_19187_.grow(j);
            p_19186_.shrink(j);
            this.setChanged();
        }

    }

    public void fromTag(ListTag pContainerNbt) {
        for (int i = 0; i < pContainerNbt.size(); ++i) {
            ItemStack itemstack = ItemStack.of(pContainerNbt.getCompound(i));
            if (!itemstack.isEmpty()) {
                this.addItem(itemstack);
            }
        }

    }

    public ListTag createTag() {
        ListTag listtag = new ListTag();

        for (int i = 0; i < this.getContainerSize(); ++i) {
            ItemStack itemstack = this.getItem(i);
            if (!itemstack.isEmpty()) {
                listtag.add(itemstack.save(new CompoundTag()));
            }
        }

        return listtag;
    }

    @Override
    public Component getName() {
        return this.title;
    }
}
