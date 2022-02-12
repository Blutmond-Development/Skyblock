package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import lombok.Getter;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MinionMenu extends AbstractContainerMenu {
    private final InvWrapper playerInvWrapper;
    private final ItemStackHandler minionInventory;
    @Getter
    private final MinionEntity minerEntity;
    private int minionSlotAmount = 0;
    private int lastHotBarIndex, lastInventoryIndex;

    public MinionMenu(int pContainerId, Inventory inventory, MinionEntity minerEntity) {
        super(SkyblockRegistries.container.minerMinionMenu.get(), pContainerId);
        this.playerInvWrapper = new InvWrapper(inventory);
        this.minionInventory = minerEntity.getInventory();
        this.minerEntity = minerEntity;

        setupMinerSlots();
        setupPlayerSlots();
    }

    private void setupPlayerSlots() {
        int index = 0;

        //Hot bar
        for (int col = 0; col < 9; col++) {
            addSlot(new SlotItemHandler(this.playerInvWrapper, index++, 8 + 18 * col, 140));
        }
        lastHotBarIndex = slots.size();

        //Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new SlotItemHandler(this.playerInvWrapper, index++, 8 + 18 * col, 82 + 18 * row));
            }
        }
        lastInventoryIndex = slots.size();
    }

    private void setupMinerSlots() {
        int upgradeX = 8;
        int upgradeY = 2;
        addSlot(new SlotItemHandler(this.minionInventory, 0, upgradeX, upgradeY));
        addSlot(new SlotItemHandler(this.minionInventory, 1, upgradeX, upgradeY += 20));
        addSlot(new SlotItemHandler(this.minionInventory, 2, upgradeX, upgradeY += 20));
        addSlot(new SlotItemHandler(this.minionInventory, 3, upgradeX, upgradeY + 20));

        int inventoryX = 44;
        int inventoryY = 20;
        for (int i = 4; i < minionInventory.getSlots(); i++) {
            int x = inventoryX + 18 * ((i - 4) % 5);
            int y = inventoryY + 18 * ((i - 4) / 5);
            addSlot(new MinionInventorySlot(this.minionInventory, i, x, y, minerEntity));
        }
        minionSlotAmount = slots.size();
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack slotItemStack = slots.get(pIndex).getItem();
        if (slotItemStack.isEmpty()) return ItemStack.EMPTY;

        if (pIndex < minionSlotAmount) {
            //Handle Minion Inventory Shift-Click
            //Check Space in Inventory
            for (int i = lastHotBarIndex; i < lastInventoryIndex; i++) {
                slotItemStack = moveToPlayerInventory(i, slotItemStack);
                //Check if there are no items left
                if (slotItemStack.isEmpty()) {
                    break;
                }
            }

            //Check Space in Hot-bar
            if (!slotItemStack.isEmpty()) {
                for (int i = minionSlotAmount; i < lastHotBarIndex; i++) {
                    slotItemStack = moveToPlayerInventory(i, slotItemStack);
                    //Check if there are no items left
                    if (slotItemStack.isEmpty()) {
                        break;
                    }
                }
            }
        } else {
            //Handle Player Inventory Shift-Click
            for (int i = 0; i < 4; i++) {
                Slot currentCurrentSlot = getSlot(i);
                //Check if slot exist
                if (currentCurrentSlot == null) {
                    break;
                }

                //Try to place Item into the Player Slot
                if (currentCurrentSlot.mayPlace(slotItemStack)) {
                    slotItemStack = currentCurrentSlot.safeInsert(slotItemStack);
                    currentCurrentSlot.setChanged();
                }
            }
        }

        Slot slot = slots.get(pIndex);
        slot.set(slotItemStack);
        slot.setChanged();
        return ItemStack.EMPTY;
    }

    private ItemStack moveToPlayerInventory(int slotIndex, ItemStack itemStack) {
        Slot currentCurrentSlot = getSlot(slotIndex);
        //Check if slot exist
        if (currentCurrentSlot == null) return itemStack;
        //Try to place Item into the Player Slot
        if (currentCurrentSlot.mayPlace(itemStack)) {
            itemStack = currentCurrentSlot.safeInsert(itemStack);
            currentCurrentSlot.setChanged();
        }

        return itemStack;
    }
}
