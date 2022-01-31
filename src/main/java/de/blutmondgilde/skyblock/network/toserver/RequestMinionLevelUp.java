package de.blutmondgilde.skyblock.network.toserver;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class RequestMinionLevelUp {
    private final int entityId;

    public RequestMinionLevelUp(FriendlyByteBuf buf) {
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

            minionEntity.levelUp(sender.getInventory());
        });
        ctx.get().setPacketHandled(true);
    }
}