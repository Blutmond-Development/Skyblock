package de.blutmondgilde.skyblock.entity.ai.goal;

import de.blutmondgilde.skyblock.entity.minion.farmer.FarmerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import java.util.Optional;

public class ReplantGoal extends Goal {
    private final FarmerEntity mob;
    private final int requiredBreakTicks;
    private BlockPos targetPos = BlockPos.ZERO;
    private boolean passed = true;
    private int breakTicks = 0;

    public ReplantGoal(FarmerEntity mob, int requiredBreakTicks) {
        this.mob = mob;
        this.requiredBreakTicks = requiredBreakTicks;
    }

    @Override
    public boolean canUse() {
        //Check if Inventory is full
        if (mob.isInventoryFull()) return false;
        //Check if target block is valid
        if (!targetPos.equals(BlockPos.ZERO)) return false;
        //Find next target
        Optional<BlockPos> nextBlockPos = BlockPos.findClosestMatch(mob.blockPosition().offset(0, 0, 0), 2, 0, pos -> {
            if (!isValidBlock(pos)) return false;
            if (!(mob.getReplacementBlock().getBlock() instanceof IPlantable plantable)) return false;
            if (!mob.level.getBlockState(pos.below()).canSustainPlant(mob.level, pos, mob.getDirection(), plantable)) return false;
            return true;
        });
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
            if (mob.level instanceof ServerLevel) {
                mob.setBreaking(false);
            }

            tryReplanting(this.targetPos, mob.getReplacementBlock());
        } else {
            if (mob.level instanceof ServerLevel) {
                mob.setBreaking(true);
            }
        }
    }

    private void tryReplanting(BlockPos pos, BlockState seedState) {
        //Check if state is a plantable block
        if (!(seedState.getBlock() instanceof IPlantable plantable)) return;
        //Check if block below can be used a foil
        if (mob.level.getBlockState(pos.below()).canSustainPlant(mob.level, pos, mob.getDirection(), plantable)) {
            mob.level.setBlockAndUpdate(pos, seedState);
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
        if (mob.level.getBlockState(pos).is(mob.getReplacementBlock().getBlock())) return false;
        return mob.level.getBlockState(pos).is(Blocks.AIR);
    }
}
