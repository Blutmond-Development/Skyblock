package de.blutmondgilde.skyblock.entity.ai.goal;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeHooks;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MineBlockGoal extends Goal {
    private final Block targetBlock;
    private final MinerEntity mob;
    private final int requiredBreakTicks;
    private BlockPos targetPos = BlockPos.ZERO;
    private boolean passed = true;
    private int breakTicks = 0;


    public MineBlockGoal(Block targetBlock, MinerEntity mob, int requiredBreakTicks) {
        this.targetBlock = targetBlock;
        this.mob = mob;
        this.requiredBreakTicks = requiredBreakTicks;
    }

    @Override
    public boolean canUse() {
        if (!targetPos.equals(BlockPos.ZERO)) return false;
        Optional<BlockPos> nextBlockPos = BlockPos.findClosestMatch(mob.blockPosition().offset(0, -1, 0), 2, 0, this::isValidBlock);
        if (nextBlockPos.isEmpty()) return false;
        this.targetPos = nextBlockPos.get();

        return ForgeHooks.canEntityDestroy(mob.level, this.targetPos, this.mob);
    }

    @Override
    public boolean canContinueToUse() {
        return !passed;
    }

    private void reset() {
        this.targetPos = BlockPos.ZERO;
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
                List<ItemStack> drops = Block.getDrops(mob.level.getBlockState(targetPos), level, this.targetPos, null, this.mob, new ItemStack(Items.NETHERITE_PICKAXE));
                Skyblock.getLogger().info("Mined {}", Arrays.toString(drops.stream().map(ItemStack::toString).toArray()));
                //TODO add drops to minion inventory
                mob.setBreaking(false);

            }
            mob.level.setBlockAndUpdate(this.targetPos, mob.getReplacementBlock().get().defaultBlockState());
        } else {
            if (mob.level instanceof ServerLevel) {
                mob.setBreaking(true);
            }
            float breakProgression = ((Integer) this.breakTicks).floatValue() / ((Integer) getMiningDuration()).floatValue() * 10F;
            this.mob.level.destroyBlockProgress(this.mob.getId(), this.targetPos, Math.min(10, Math.round(breakProgression - 1)));
        }
    }

    public int getMiningDuration() {
        return requiredBreakTicks;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Checks the {@link Block} of given {@link BlockPos} equals the {@link MineBlockGoal#targetBlock}
     *
     * @param pos Position to check
     */
    private boolean isValidBlock(BlockPos pos) {
        if (pos.equals(mob.blockPosition().below())) return false; //Prevent the Miner from mining down
        return mob.level.getBlockState(pos).getBlock().equals(this.targetBlock);
    }
}
