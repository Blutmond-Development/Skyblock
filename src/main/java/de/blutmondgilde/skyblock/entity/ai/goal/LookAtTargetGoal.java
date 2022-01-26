package de.blutmondgilde.skyblock.entity.ai.goal;

import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class LookAtTargetGoal extends Goal {
    protected final MinerEntity mob;
    @Nullable
    protected BlockPos lookAt;
    private int lookTime;
    protected final float probability = 0.02F;

    public LookAtTargetGoal(MinerEntity pMob) {
        this.mob = pMob;
    }

    /**
     * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
     * method as well.
     */
    public boolean canUse() {
        if (this.mob.getRandom().nextFloat() >= this.probability) {
            return false;
        } else {
            if (this.mob.getTargetBlock() != null && !this.mob.getTargetBlock().equals(BlockPos.ZERO)) {
                this.lookAt = this.mob.getTargetBlock();
            }
            return this.lookAt != null;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean canContinueToUse() {
        if (this.lookAt.equals(BlockPos.ZERO)) {
            return false;
        } else if (this.mob.distanceToSqr(Vec3.atCenterOf(this.lookAt)) > 9.0) {
            return false;
        } else {
            return this.lookTime > 0;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void start() {
        this.lookTime = this.adjustedTickDelay(40 + this.mob.getRandom().nextInt(40));
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void stop() {
        this.lookAt = null;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void tick() {
        if (!this.lookAt.equals(BlockPos.ZERO)) {
            this.mob.getLookControl().setLookAt(this.lookAt.getX(), this.lookAt.getY(), this.lookAt.getZ());
            --this.lookTime;
        }
    }
}
