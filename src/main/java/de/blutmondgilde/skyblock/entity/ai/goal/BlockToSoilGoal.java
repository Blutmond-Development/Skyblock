package de.blutmondgilde.skyblock.entity.ai.goal;

import com.mojang.authlib.GameProfile;
import de.blutmondgilde.skyblock.entity.minion.farmer.FarmerEntity;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class BlockToSoilGoal extends Goal {
    @Nullable
    private final FakePlayer fakePlayer;
    private final FarmerEntity mob;
    private final int requiredBreakTicks;
    private BlockPos targetPos = BlockPos.ZERO;
    private boolean passed = true;
    private int breakTicks = 0;

    public BlockToSoilGoal(FarmerEntity mob, int requiredBreakTicks) {
        this.mob = mob;
        this.requiredBreakTicks = requiredBreakTicks;
        if (mob.level instanceof ServerLevel level) {
            fakePlayer = new FakePlayer(level, new GameProfile(UUID.randomUUID(), mob.getDisplayName().getString()));
        } else {
            fakePlayer = null;
        }
    }

    @Override
    public boolean canUse() {
        //Check if Inventory is full
        if (mob.isInventoryFull()) return false;
        //Check if target block is valid
        if (!targetPos.equals(BlockPos.ZERO)) return false;
        //Find next target
        Optional<BlockPos> nextBlockPos = BlockPos.findClosestMatch(mob.blockPosition().offset(0, -1, 0), 2, 0, this::isValidBlock);
        if (nextBlockPos.isEmpty()) return false;
        //Set next target
        this.targetPos = nextBlockPos.get();
        mob.setTargetBlock(nextBlockPos.get());

        return ForgeHooks.canEntityDestroy(mob.level, this.targetPos, this.mob);
    }

    @Override
    public boolean canContinueToUse() {
        return !passed;
    }

    private void reset() {
        this.targetPos = BlockPos.ZERO;
        mob.setTargetBlock(BlockPos.ZERO);
        this.passed = true;
        this.breakTicks = 0;
    }

    @Override
    public void start() {
        this.passed = false;
    }

    @Override
    public void stop() {
        this.mob.level.destroyBlockProgress(this.mob.getId(), this.targetPos, -1);
        reset();
    }

    @Override
    public void tick() {
        if (!isValidBlock(this.targetPos)) {
            reset();
            return;
        }

        this.breakTicks++;
        if (this.breakTicks >= getMiningDuration()) {
            //finish
            if (mob.level instanceof ServerLevel level) {
                mob.setBreaking(false);
                InteractionResult result = Items.NETHERITE_HOE.useOn(new UseOnContext(fakePlayer, InteractionHand.MAIN_HAND, new BlockHitResult(new Vec3(0, 0, 0), Direction.UP, targetPos, false)));

                if (!result.equals(InteractionResult.SUCCESS)) {
                    BlockState blockAbove = level.getBlockState(targetPos.above());
                    boolean canBeReplaced = blockAbove.canBeReplaced(new BlockPlaceContext(fakePlayer, InteractionHand.MAIN_HAND, new ItemStack(Items.NETHERITE_HOE),
                        new BlockHitResult(new Vec3(0, 0, 0), Direction.UP, targetPos, false)));
                    if (canBeReplaced) {
                        level.setBlockAndUpdate(targetPos.above(), Blocks.AIR.defaultBlockState());
                    }
                }
                reset();
            }
        } else {
            if (mob.level instanceof ServerLevel) {
                mob.setBreaking(true);
            }
        }
    }

    public int getMiningDuration() {
        return requiredBreakTicks - Math.round(requiredBreakTicks * mob.getFuelTimer().getCurrentModifier());
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    private boolean isValidBlock(BlockPos pos) {
        if (pos.equals(mob.blockPosition().below())) return false; //Prevent the Miner from mining down
        if (!mob.level.getBlockState(pos.above()).is(Blocks.AIR)) {
            boolean canBeReplaced = mob.level.getBlockState(pos.above()).canBeReplaced(new BlockPlaceContext(fakePlayer, InteractionHand.MAIN_HAND, new ItemStack(Items.NETHERITE_HOE),
                new BlockHitResult(new Vec3(0, 0, 0), Direction.UP, targetPos, false)));
            if (!canBeReplaced) return false;
        }

        BlockState state = mob.level.getBlockState(pos);
        return state.is(SkyblockTags.Blocks.TILLABLE);
    }
}
