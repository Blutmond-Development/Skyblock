package de.blutmondgilde.skyblock.entity.minion.miner;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.container.MinerMinionInventory;
import de.blutmondgilde.skyblock.container.MinerMinionMenu;
import de.blutmondgilde.skyblock.entity.ai.goal.LookAtTargetGoal;
import de.blutmondgilde.skyblock.entity.ai.goal.MineBlockGoal;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.Tags;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class MinerEntity extends Mob implements OwnableEntity, IAnimatable, Npc, ContainerListener {
    private static final EntityDataAccessor<Boolean> BREAKING = SynchedEntityData.defineId(MinerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> LEVEL = SynchedEntityData.defineId(MinerEntity.class, EntityDataSerializers.INT);
    @Getter
    private UUID ownerUUID = null;
    private final AnimationFactory animationFactory = new AnimationFactory(this);
    private int replacementCounter = 100;
    private Optional<BlockPos> nextReplacement = Optional.empty();
    @Getter
    private final ItemStackHandler inventory;
    @Getter
    @Setter
    private BlockPos targetBlock = BlockPos.ZERO;
    @Setter
    @Getter
    private boolean inventoryFull = false;

    public MinerEntity(EntityType<MinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        inventory = new MinerMinionInventory(getInventorySize());
    }

    public static AttributeSupplier.Builder setCustomAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20.0)
            .add(Attributes.ATTACK_SPEED)
            .add(ForgeMod.REACH_DISTANCE.get(), 2)
            .add(Attributes.KNOCKBACK_RESISTANCE, 1);
    }

    protected int getInventorySize() {
        return switch (entityData.get(LEVEL)) {
            default -> 1;
            case 2 -> 3;
            case 4 -> 6;
            case 6 -> 9;
            case 8 -> 12;
            case 10 -> 15;
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BREAKING, false);
        this.entityData.define(LEVEL, 1);
    }

    public int getMinionLevel() {
        return entityData.get(LEVEL);
    }

    public void levelUp() {
        int level = getMinionLevel() + 1;
        entityData.set(LEVEL, level);
        inventory.setSize(getInventorySize());
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new MineBlockGoal(getMiningTarget().get(), this, 20 * 15));
        goalSelector.addGoal(1, new LookAtTargetGoal(this));
    }

    public abstract Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget();

    public abstract Supplier<Block> getReplacementBlock();

    @Nullable
    @Override
    public Entity getOwner() {
        return level.getPlayerByUUID(ownerUUID);
    }

    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
        setCustomName(new TranslatableComponent(Skyblock.MOD_ID + ".entity.miner", getOwner().getDisplayName(), getResultName()));
    }

    protected abstract MutableComponent getResultName();

    @Override
    public void tick() {
        super.tick();
        if (this.ownerUUID == null) {
            die(DamageSource.OUT_OF_WORLD);
            markHurt();
            return;
        }

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
        pCompound.putUUID("owner", this.ownerUUID);
        pCompound.putBoolean("breaking", this.entityData.get(BREAKING));
        pCompound.putInt("level", this.entityData.get(LEVEL));
        pCompound.put("inventory", this.inventory.serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.ownerUUID = pCompound.getUUID("owner");
        this.entityData.set(BREAKING, pCompound.getBoolean("breaking"));
        this.entityData.set(LEVEL, pCompound.getInt("level"));
        this.inventory.deserializeNBT(pCompound.getCompound("inventory"));
    }

    public void setBreaking(boolean value) {
        this.entityData.set(BREAKING, value);
    }

    @Override
    protected InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (isAlive() && !pPlayer.isSecondaryUseActive() && pPlayer.getUUID().equals(getOwnerUUID())) {
            openInventoryScreen(pPlayer);
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(pPlayer, pHand);
    }

    private void openInventoryScreen(Player pPlayer) {
        if (pPlayer instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openGui(serverPlayer, new SimpleMenuProvider((pContainerId, pInventory, pPlayer1) ->
                new MinerMinionMenu(pContainerId, pPlayer1.getInventory(), this), getCustomName()), buf -> buf.writeInt(getId()));
        }
    }

    @Override
    public void containerChanged(Container pInvBasic) {
        //???
    }
}
