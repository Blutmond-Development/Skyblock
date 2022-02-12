package de.blutmondgilde.skyblock.network.toserver;

import de.blutmondgilde.skyblock.container.MinionInventory;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.Set;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class CollectMinionItems {
    private final int entityId;

    public CollectMinionItems(FriendlyByteBuf buf) {
        entityId = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(this.entityId);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer sender = ctx.get().getSender();
            Entity entity = sender.getLevel().getEntity(entityId);
            if (entity == null) {
                sender.sendMessage(new TextComponent("ERROR! Could not load Minion Entity with id " + entityId).withStyle(ChatFormatting.RED), Util.NIL_UUID);
                return;
            }

            if (!(entity instanceof MinionEntity minionEntity)) {
                sender.sendMessage(new TextComponent("ERROR! Loaded Entity is not a Minion Entity.").withStyle(ChatFormatting.RED), Util.NIL_UUID);
                return;
            }

            MinionInventory minionInventory = minionEntity.getInventory();
            Inventory inventory = sender.getInventory();

            for (int i = 4; i < minionInventory.getSlots(); i++) {
                //ItemStack in Slot
                ItemStack slotItem = minionInventory.getStackInSlot(i);
                //Check if Inventory already has a ItemStack with that Items
                if (inventory.hasAnyOf(Set.of(slotItem.getItem()))) {
                    //Search Slot with ItemStack
                    int playerInventorySlot = inventory.getSlotWithRemainingSpace(slotItem);
                    if (playerInventorySlot != -1) {
                        //Get ItemStack from Player Inventory
                        ItemStack itemStackInPlayerInventory = inventory.getItem(playerInventorySlot);
                        //Calculate amount of the given ItemStack to transfer
                        int transferAmount = Math.min(itemStackInPlayerInventory.getMaxStackSize() - itemStackInPlayerInventory.getCount(), slotItem.getCount());
                        //Transfer Items
                        slotItem.grow(-transferAmount);
                        itemStackInPlayerInventory.grow(transferAmount);
                        //Update Player Inventory
                        inventory.setItem(playerInventorySlot, itemStackInPlayerInventory);
                    }
                }

                if (slotItem.getCount() <= 0 || slotItem.isEmpty()) {
                    minionInventory.setStackInSlot(i, ItemStack.EMPTY);
                    inventory.setChanged();
                    continue;
                }

                //Try placing the Stack in an empty slot
                int playerSlot = inventory.getFreeSlot();
                if (playerSlot != -1) {
                    inventory.setItem(playerSlot, slotItem);
                    inventory.setChanged();
                    minionInventory.setStackInSlot(i, ItemStack.EMPTY);
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}