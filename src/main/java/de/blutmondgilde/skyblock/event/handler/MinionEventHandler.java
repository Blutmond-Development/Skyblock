package de.blutmondgilde.skyblock.event.handler;


import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.item.PickedMinionItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class MinionEventHandler {
    public static void init(IEventBus forgeBus) {
        forgeBus.addListener(MinionEventHandler::onMinionHit);
        forgeBus.addListener(MinionEventHandler::onTakeItems);
    }

    private static void onMinionHit(LivingDamageEvent e) {
        if (!e.getSource().isBypassInvul()) {
            e.setAmount(0F);
            e.setCanceled(true);
        }

        if (!(e.getEntityLiving() instanceof MinionEntity minion)) return;

        Entity sourceEntity = e.getSource().getEntity();
        if (!(sourceEntity instanceof Player)) {
            sourceEntity = e.getSource().getDirectEntity();
        }
        if (!(sourceEntity instanceof Player player)) return;

        if (minion.getOwnerUUID() == null) return;
        if (!minion.getOwnerUUID().equals(sourceEntity.getUUID())) return;
        if (!player.addItem(PickedMinionItem.createNew(minion))) {
            ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), PickedMinionItem.createNew(minion));
            player.level.addFreshEntity(itemEntity);
        }
    }

    private static void onTakeItems(final MinionEvent.TakeItemStack e) {
        Skyblock.getLogger().info("Player {} took {} from {}.", e.getPlayer(), e.getItemStack(), e.getMinion().getDisplayName().getString());
    }
}
