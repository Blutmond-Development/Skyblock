package de.blutmondgilde.skyblock.container;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.event.handler.MinionEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class MinionInventorySlot extends SlotItemHandler {
    private final MinionEntity minion;

    public MinionInventorySlot(IItemHandler itemHandler, int index, int xPosition, int yPosition, MinionEntity minion) {
        super(itemHandler, index, xPosition, yPosition);
        this.minion = minion;
    }

    @Override
    public boolean mayPlace(@NotNull ItemStack stack) {
        return false;
    }

    @Override
    public void onTake(Player pPlayer, ItemStack pStack) {
        MinionEvent.TakeItemStack takeItemStack = new MinionEvent.TakeItemStack(minion, pStack, pPlayer);
        MinecraftForge.EVENT_BUS.post(takeItemStack);
        super.onTake(pPlayer, takeItemStack.getItemStack());
    }
}
