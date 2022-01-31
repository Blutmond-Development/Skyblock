package de.blutmondgilde.skyblock.entity.minion.miner;

import de.blutmondgilde.skyblock.entity.ai.goal.LookAtTargetGoal;
import de.blutmondgilde.skyblock.entity.ai.goal.MineBlockGoal;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;
import java.util.function.Supplier;

public abstract class MinerEntity extends MinionEntity {
    private static final EntityDataAccessor<Boolean> BREAKING = SynchedEntityData.defineId(MinerEntity.class, EntityDataSerializers.BOOLEAN);
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private int replacementCounter = 100;
    private Optional<BlockPos> nextReplacement = Optional.empty();
    @Getter
    @Setter
    private BlockPos targetBlock = BlockPos.ZERO;

    public MinerEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.ATTACK_SPEED)
            .add(ForgeMod.REACH_DISTANCE.get(), 2)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BREAKING, false);
    }

    @Override
    protected void addGoals() {
        goalSelector.addGoal(1, new MineBlockGoal(getMiningTarget().get(), this, 20 * 15));
        goalSelector.addGoal(1, new LookAtTargetGoal(this));
    }

    public abstract Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget();

    public abstract Supplier<Block> getReplacementBlock();

    @Override
    public void tick() {
        super.tick();

        if (nextReplacement.isPresent()) {
            this.replacementCounter--;
            if (replacementCounter == 0) {
                replacementCounter = 100;
                level.setBlockAndUpdate(nextReplacement.get(), getMiningTarget().get().getRandomElement(random).defaultBlockState());
                this.nextReplacement = Optional.empty();
            }
        } else {
            this.nextReplacement = BlockPos.findClosestMatch(blockPosition().offset(0, -1, 0), 2, 0, this::isValidBlock);
            this.replacementCounter = 100;
        }
    }

    private boolean isValidBlock(BlockPos pos) {
        if (pos.equals(blockPosition().below())) return false; //Prevent the Miner from mining down
        return level.getBlockState(pos).getBlock().equals(this.getReplacementBlock().get());
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "idle_controller", 0, this::checkAnimation));
    }

    private PlayState checkAnimation(AnimationEvent<MinerEntity> e) {
        AnimationController<MinerEntity> controller = e.getController();

        if (this.entityData.get(BREAKING)) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.miner.break", true));
            return PlayState.CONTINUE;
        }

        controller.setAnimation(new AnimationBuilder().addAnimation("animation.miner.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return this.animationFactory;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("breaking", this.entityData.get(BREAKING));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(BREAKING, pCompound.getBoolean("breaking"));
    }

    public void setBreaking(boolean value) {
        this.entityData.set(BREAKING, value);
    }
}
