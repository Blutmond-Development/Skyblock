package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.awt.Color;
import java.util.Objects;

public class MinerSpawnEggItem extends ForgeSpawnEggItem {
    private static final Color backgroundColor = new Color(94, 94, 94);
    private static final Color highlightColor = new Color(23, 23, 23);

    public MinerSpawnEggItem() {
        super(SkyblockRegistries.entities.coalMiner, backgroundColor.getRGB(), highlightColor.getRGB(), new Properties().tab(SkyblockTabs.MINIONS));
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemstack = pContext.getItemInHand();
            BlockPos blockpos = pContext.getClickedPos();
            Direction direction = pContext.getClickedFace();
            BlockState blockstate = level.getBlockState(blockpos);

            BlockPos blockpos1;
            if (blockstate.getCollisionShape(level, blockpos).isEmpty()) {
                blockpos1 = blockpos;
            } else {
                blockpos1 = blockpos.relative(direction);
            }

            EntityType<?> entitytype = this.getType(itemstack.getTag());
            Entity spawnEntity = entitytype.spawn((ServerLevel) level, itemstack, pContext.getPlayer(), blockpos1, MobSpawnType.SPAWN_EGG,
                true, !Objects.equals(blockpos, blockpos1) && direction == Direction.UP);

            if (spawnEntity != null) {
                ((MinerEntity) spawnEntity).setOwnerUUID(pContext.getPlayer().getUUID());
                itemstack.shrink(1);
                level.gameEvent(pContext.getPlayer(), GameEvent.ENTITY_PLACE, blockpos);
            }

            return InteractionResult.CONSUME;
        }
    }

}