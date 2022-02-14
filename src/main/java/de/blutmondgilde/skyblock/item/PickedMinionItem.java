package de.blutmondgilde.skyblock.item;

import de.blutmondgilde.skyblock.client.renderer.PickedMinionItemRenderer;
import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTabs;
import lombok.Getter;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class PickedMinionItem extends Item implements IAnimatable, ISyncable {
    @Getter
    public AnimationFactory factory = new AnimationFactory(this);

    public PickedMinionItem() {
        super(new Properties().stacksTo(1).tab(SkyblockTabs.MINIONS));
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context) {
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        Level level = context.getLevel();
        if (!placeMinion(player, pos, direction, level, stack)) return InteractionResult.FAIL;
        return InteractionResult.SUCCESS;
    }

    private boolean placeMinion(Player player, BlockPos position, Direction facing, Level world, ItemStack stack) {
        if (world.isClientSide()) return false;
        if (!hasMinionTag(stack)) return false;
        MinionEntity minionEntity = getEntityFromStack(stack, world);
        if (minionEntity == null) return false;
        position = position.relative(facing);
        minionEntity.setPos(position.getX() + 0.5, position.getY(), position.getZ() + 0.5);
        minionEntity.setXRot(0);
        minionEntity.setYRot(0);
        minionEntity.setOwnerUUID(player.getUUID());
        world.addFreshEntity(minionEntity);
        player.getInventory().removeItem(stack);
        return true;
    }

    public static ItemStack createNew(MinionEntity entity) {
        CompoundTag tag = new CompoundTag();
        tag.putString("minion", EntityType.getKey(entity.getType()).toString());
        entity.addAdditionalSaveData(tag);
        entity.remove(Entity.RemovalReason.DISCARDED);
        ItemStack itemStack = new ItemStack(SkyblockRegistries.items.pickedMinion.get());
        itemStack.setTag(tag);
        return itemStack;
    }

    @Nullable
    private MinionEntity getEntityFromStack(ItemStack stack, Level world) {
        if (stack.hasTag()) {
            EntityType<?> minionEntityType = EntityType.byString(stack.getTag().getString("minion")).orElse(null);
            if (minionEntityType != null) {
                Entity entity = minionEntityType.create(world);
                if (entity instanceof MinionEntity minionEntity) {
                    if (stack.getTag().contains("level")) {
                        minionEntity.readAdditionalSaveData(stack.getTag());
                    }
                    return minionEntity;
                }
            }
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (hasMinionTag(pStack)) {
            CompoundTag tag = pStack.getTag();
            pTooltipComponents.add(new TranslatableComponent("item.skyblock.picked_minion.tooltip.type", new TranslatableComponent(tag.getString("minion").replace(':', '.'))));
            pTooltipComponents.add(new TranslatableComponent("item.skyblock.picked_minion.tooltip.level", tag.getInt("level")));
        } else {
            pTooltipComponents.add(new TranslatableComponent("item.skyblock.picked_minion.tooltip.notag").withStyle(Style.EMPTY.withColor(ChatFormatting.RED).withBold(true)));
        }
    }

    private boolean hasMinionTag(ItemStack itemStack) {
        if (!itemStack.hasTag()) return false;
        return itemStack.getTag().contains("minion");
    }

    private PlayState predicate(AnimationEvent<PickedMinionItem> event) {
        AnimationController<PickedMinionItem> controller = event.getController();
        controller.setAnimation(new AnimationBuilder().addAnimation("animation.miner.idle", true));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, this::predicate));
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == 0) {
            final AnimationController controller = GeckoLibUtil.getControllerForID(factory, id, "controller");

            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.miner.idle", true));
            }
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final BlockEntityWithoutLevelRenderer renderer = new PickedMinionItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }
}
