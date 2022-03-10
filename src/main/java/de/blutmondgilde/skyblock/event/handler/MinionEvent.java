package de.blutmondgilde.skyblock.event.handler;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;

@RequiredArgsConstructor
public class MinionEvent extends Event {
    @Getter
    private final MinionEntity minion;

    public static class TakeItemStack extends MinionEvent {
        @Getter
        @Setter
        private ItemStack itemStack;
        @Getter
        private final Player player;

        public TakeItemStack(MinionEntity minion, ItemStack itemStack, Player player) {
            super(minion);
            this.itemStack = itemStack;
            this.player = player;
        }
    }

    public static class LevelUp extends MinionEvent{

        public LevelUp(MinionEntity minion) {
            super(minion);
        }
    }
}
