package de.blutmondgilde.skyblock.event.handler;


import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

        if (!(e.getEntityLiving() instanceof MinerEntity miner)) return;

        Entity sourceEntity = e.getSource().getEntity();
        if (!(sourceEntity instanceof Player)) {
            sourceEntity = e.getSource().getDirectEntity();
        }
        if (!(sourceEntity instanceof Player player)) return;

        if (miner.getOwnerUUID() == null) return;
        if (!miner.getOwnerUUID().equals(sourceEntity.getUUID())) return;
        if (!player.addItem(new ItemStack(SkyblockRegistries.items.minerSpawnEgg.get()))) {
            ItemEntity itemEntity = new ItemEntity(player.level, player.getX(), player.getY(), player.getZ(), new ItemStack(SkyblockRegistries.items.minerSpawnEgg.get()));
            player.level.addFreshEntity(itemEntity);
        }
        e.getEntityLiving().kill();
    }

    private static void onTakeItems(final MinionEvent.TakeItemStack e) {
        Skyblock.getLogger().info("Player {} took {} from {}.", e.getPlayer(), e.getItemStack(), e.getMinion().getDisplayName().getString());
    }
}
