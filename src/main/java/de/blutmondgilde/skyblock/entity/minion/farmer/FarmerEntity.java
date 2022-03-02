package de.blutmondgilde.skyblock.entity.minion.farmer;

import de.blutmondgilde.skyblock.entity.ai.goal.BlockToSoilGoal;
import de.blutmondgilde.skyblock.entity.ai.goal.BreakAndReplantGoal;
import de.blutmondgilde.skyblock.entity.ai.goal.ReplantGoal;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.entity.minion.miner.MinerEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Predicate;

public abstract class FarmerEntity extends MinionEntity {
    public static final EntityDataAccessor<Boolean> BREAKING = SynchedEntityData.defineId(MinerEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    @Setter
    @Getter
    private BlockPos nextCrop = BlockPos.ZERO;

    public FarmerEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void addGoals() {
        if (canTillBlocks()) goalSelector.addGoal(1, new BlockToSoilGoal(this, 20 * 15));
        goalSelector.addGoal(1, new ReplantGoal(this, 20 * 15));
        goalSelector.addGoal(1, new BreakAndReplantGoal(IsCorrectBlock(), this, 20 * 15));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BREAKING, false);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "idle_controller", 0, this::checkAnimation));
    }

    private PlayState checkAnimation(AnimationEvent<FarmerEntity> e) {
        AnimationController<FarmerEntity> controller = e.getController();

        if (this.entityData.get(BREAKING)) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.farmer.break", true));
            return PlayState.CONTINUE;
        }

        controller.setAnimation(new AnimationBuilder().addAnimation("animation.farmer.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    public abstract Predicate<BlockState> IsCorrectBlock();

    public abstract BlockState getReplacementBlock();

    public void setBreaking(boolean value) {
        this.entityData.set(BREAKING, value);
    }

    protected boolean canTillBlocks() {
        return true;
    }
}
