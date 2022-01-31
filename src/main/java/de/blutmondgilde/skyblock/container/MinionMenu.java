package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import lombok.Getter;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MinionMenu extends AbstractContainerMenu {
    private final InvWrapper playerInvWrapper;
    private final ItemStackHandler minionInventory;
    @Getter
    private final MinionEntity minerEntity;

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

        for (int col = 0; col < 9; col++) {
            addSlot(new SlotItemHandler(this.playerInvWrapper, index++, 8 + 18 * col, 140));
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new SlotItemHandler(this.playerInvWrapper, index++, 8 + 18 * col, 82 + 18 * row));
            }
        }
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
            addSlot(new MinionInventorySlot(this.minionInventory, i, inventoryX, inventoryY, minerEntity));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
    }
}
