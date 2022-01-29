package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class MinerMinionMenu extends AbstractContainerMenu {
    private final InvWrapper playerInvWrapper;
    private final ItemStackHandler minionInventory;
    private final MinerEntity minerEntity;

    public MinerMinionMenu(int pContainerId, Inventory inventory, MinerEntity minerEntity) {
        super(SkyblockRegistries.container.minerMinionMenu.get(), pContainerId);
        this.playerInvWrapper = new InvWrapper(inventory);
        this.minionInventory = minerEntity.getInventory();
        this.minerEntity = minerEntity;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
