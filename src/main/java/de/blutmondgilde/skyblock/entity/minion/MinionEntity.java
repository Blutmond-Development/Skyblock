package de.blutmondgilde.skyblock.entity.minion;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.container.MinionInventory;
import de.blutmondgilde.skyblock.container.MinionMenu;
import de.blutmondgilde.skyblock.entity.ai.goal.LookAtTargetGoal;
import de.blutmondgilde.skyblock.fuel.MinionFuel;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.util.FuelTimer;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.world.ForgeChunkManager;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public abstract class MinionEntity extends Mob implements OwnableEntity, IAnimatable, Npc {
    private static final EntityDataAccessor<Integer> LEVEL = SynchedEntityData.defineId(MinionEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<CompoundTag> FUEL_TIMER = SynchedEntityData.defineId(MinionEntity.class, EntityDataSerializers.COMPOUND_TAG);
    @Getter
    protected UUID ownerUUID = null;
    @Getter
    protected final MinionInventory inventory;
    @Getter
    @Setter
    private BlockPos targetBlock = BlockPos.ZERO;
    private final ArrayList<LevelChunk> loadedChunks = new ArrayList<>();

    public MinionEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        inventory = new MinionInventory(getInventorySize());
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
            case 2, 3 -> 3;
            case 4, 5 -> 6;
            case 6, 7 -> 9;
            case 8, 9 -> 12;
            case 10, 11, 12 -> 15;
        };
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LEVEL, 1);
        this.entityData.define(FUEL_TIMER, new FuelTimer().serializeNBT());
    }

    /**
     * Returns a {@link Map} with the required {@link Item} and the required count
     */
    public abstract Map<Item, Integer> getItemsForLevelUp();

    protected boolean canLevelUp(Inventory inventory, boolean simulate) {
        //Skip if minion is max level
        if (getMinionLevel() >= 12) {
            return false;
        }

        if (!inventory.player.isCreative()) {
            //Remove Items from Player Inventory
            InvWrapper wrapper = new InvWrapper(inventory);
            Map<Integer, ItemStack> updatedSlots = new TreeMap<>();

            for (Map.Entry<Item, Integer> entry : getItemsForLevelUp().entrySet()) {
                Item item = entry.getKey();
                int remainingItems = entry.getValue();

                for (int i = 0; i < wrapper.getSlots() && remainingItems > 0; i++) {
                    if (wrapper.getStackInSlot(i).getItem().equals(item)) {
                        //Get ItemStack from Slot
                        ItemStack foundStack = wrapper.getStackInSlot(i);
                        //Get modified ItemStack or new ItemStack
                        ItemStack newStack = updatedSlots.containsKey(i) ? updatedSlots.get(i) : foundStack.copy();

                        //Check if current ItemStack is empty
                        if (newStack != ItemStack.EMPTY) {
                            int oldCount = newStack.getCount();
                            //Update ItemStack
                            newStack.setCount(oldCount - remainingItems);
                            remainingItems -= oldCount - newStack.getCount();

                            //change ItemStack to empty if count is 0
                            if (newStack.getCount() == 0) {
                                newStack = ItemStack.EMPTY;
                            }

                            //add remaining amount
                            if (newStack.getCount() < 0) {
                                remainingItems += (newStack.getCount() * (-1));
                            }

                            updatedSlots.put(i, newStack);
                        }
                    }
                }

                if (remainingItems > 0) {
                    return false;
                }
            }

            if (!simulate) {
                //Update Inventory
                updatedSlots.forEach(wrapper::setStackInSlot);
            }
        }

        return true;
    }

    public boolean canLevelUp(ServerPlayer serverPlayer) {
        return canLevelUp(serverPlayer.getInventory(), true);
    }

    public List<MutableComponent> getLevelUpMaterialList(Inventory inventory) {
        //Skip if minion is max level
        if (getMinionLevel() >= 12) {
            return List.of(new TranslatableComponent("skyblock.minion.level.up.max"));
        }

        InvWrapper wrapper = new InvWrapper(inventory);
        Map<Integer, ItemStack> updatedSlots = new TreeMap<>();
        Map<Item, Integer> playerHas = new HashMap<>();

        for (Map.Entry<Item, Integer> entry : getItemsForLevelUp().entrySet()) {
            Item item = entry.getKey();
            int remainingItems = entry.getValue();

            for (int i = 0; i < wrapper.getSlots() && remainingItems > 0; i++) {
                if (wrapper.getStackInSlot(i).getItem().equals(item)) {
                    //Get ItemStack from Slot
                    ItemStack foundStack = wrapper.getStackInSlot(i);
                    //Get modified ItemStack or new ItemStack
                    ItemStack newStack = updatedSlots.containsKey(i) ? updatedSlots.get(i) : foundStack.copy();

                    //Check if current ItemStack is empty
                    if (newStack != ItemStack.EMPTY) {
                        int oldCount = newStack.getCount();
                        //Update ItemStack
                        newStack.setCount(oldCount - remainingItems);
                        remainingItems -= oldCount - newStack.getCount();

                        //change ItemStack to empty if count is 0
                        if (newStack.getCount() == 0) {
                            newStack = ItemStack.EMPTY;
                        }

                        //add remaining amount
                        if (newStack.getCount() < 0) {
                            remainingItems += (newStack.getCount() * (-1));
                        }

                        updatedSlots.put(i, newStack);
                    }
                }
            }

            playerHas.put(item, entry.getValue() - remainingItems);
        }

        List<MutableComponent> result = new ArrayList<>();
        final Color green = new Color(1, 203, 15);
        final Color red = new Color(255, 0, 0);
        getItemsForLevelUp().forEach((item, requiredCount) -> {
            if (playerHas.containsKey(item)) {
                int amount = playerHas.get(item);
                result.add(new TranslatableComponent("skyblock.minion.level.up.requirements", amount, requiredCount, item.getName(new ItemStack(item))).withStyle(
                    Style.EMPTY.withColor(amount >= requiredCount ? green.getRGB() : red.getRGB())));
            } else {
                result.add(new TranslatableComponent("skyblock.minion.level.up.requirements", 0, requiredCount, item.getName(new ItemStack(item))).withStyle(Style.EMPTY.withColor(red.getRGB())));
            }
        });

        return result;
    }

    public int getMinionLevel() {
        return entityData.get(LEVEL);
    }

    public FuelTimer getFuelTimer() {
        FuelTimer fuelTimer = new FuelTimer();
        fuelTimer.deserializeNBT(entityData.get(FUEL_TIMER));
        return fuelTimer;
    }

    @Override
    public void remove(RemovalReason pReason) {
        if (level instanceof ServerLevel serverLevel) {
            loadedChunks.forEach(levelChunk -> {
                ForgeChunkManager.forceChunk(serverLevel, Skyblock.MOD_ID, this, levelChunk.getPos().x, levelChunk.getPos().z, false, true);
            });
        }
        super.remove(pReason);
    }

    public void levelUp(Inventory inventory) {
        if (canLevelUp(inventory, true)) {
            canLevelUp(inventory, false);
            int level = getMinionLevel() + 1;
            entityData.set(LEVEL, level);
            this.inventory.setNewSize(getInventorySize());
        } else {
            inventory.player.sendMessage(new TranslatableComponent("skyblock.upgrade.material.missing").withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.RED), Util.NIL_UUID);
        }
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
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public void push(Entity pEntity) {

    }

    @Override
    public void push(double pX, double pY, double pZ) {

    }

    @Override
    public boolean ignoreExplosion() {
        return true;
    }

    protected abstract void addGoals();

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(2, new LookAtTargetGoal(this));
        addGoals();
    }

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
        }

        FuelTimer fuelTimer = getFuelTimer();
        if (this.inventory.hasFuelItem()) {
            MinionFuel fuel = SkyblockRegistries.minionFuel.findByItem(this.inventory.getFuelSlotStack().getItem());
            if (fuel != null) {
                if (fuelTimer.setFuel(fuel, this.inventory.getFuelSlotStack(), this.level)) {
                    entityData.set(FUEL_TIMER, fuelTimer.serializeNBT());
                    this.inventory.consumeFuel();
                }
            }
        }

        fuelTimer.tick();
        entityData.set(FUEL_TIMER, fuelTimer.serializeNBT());

        if (level instanceof ServerLevel serverLevel) {
            if (loadedChunks.isEmpty()) {
                for (int x = -2; x < 3; x++) {
                    for (int z = -2; z < 3; z++) {
                        BlockPos workingPos = blockPosition().offset(x, 0, z);
                        LevelChunk workingChunk = serverLevel.getChunkAt(workingPos);
                        if (!loadedChunks.contains(workingChunk)) loadedChunks.add(workingChunk);
                    }
                }

                for (LevelChunk levelChunk : loadedChunks) {
                    ForgeChunkManager.forceChunk(serverLevel, Skyblock.MOD_ID, this, levelChunk.getPos().x, levelChunk.getPos().z, true, true);
                }
            }
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putUUID("owner", this.ownerUUID);
        pCompound.putInt("level", this.entityData.get(LEVEL));
        pCompound.put("inventory", this.inventory.serializeNBT());
        pCompound.put("fueltimer", this.getFuelTimer().serializeNBT());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.ownerUUID = pCompound.getUUID("owner");
        this.entityData.set(LEVEL, pCompound.getInt("level"));
        this.inventory.deserializeNBT(pCompound.getCompound("inventory"));
        this.entityData.set(FUEL_TIMER, pCompound.getCompound("fueltimer"));
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
                new MinionMenu(pContainerId, pPlayer1.getInventory(), this), getCustomName()), buf -> buf.writeInt(getId()));
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if (pKey.equals(LEVEL)) {
            inventory.setNewSize(getInventorySize());
        }
    }

    @Override
    public void checkDespawn() {
        //Prevent Minions from despawning
    }
}
