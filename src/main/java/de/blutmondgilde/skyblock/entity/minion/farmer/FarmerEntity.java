package de.blutmondgilde.skyblock.entity.minion.farmer;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Supplier;

public abstract class FarmerEntity extends MinionEntity {
    private final AnimationFactory animationFactory = new AnimationFactory(this);

    public FarmerEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void addGoals() {
        //TODO farming goal

    }


    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return animationFactory;
    }

    public abstract Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget();

    public abstract Supplier<Block> getReplacementBlock();
}
