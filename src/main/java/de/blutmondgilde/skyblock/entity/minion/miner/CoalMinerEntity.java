package de.blutmondgilde.skyblock.entity.minion.miner;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.function.Supplier;

public class CoalMinerEntity extends MinerEntity {
    public CoalMinerEntity(EntityType<MinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> Tags.Blocks.ORES_COAL;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.COBBLESTONE;
    }

    @Override
    protected MutableComponent getResultName() {
        return Items.COAL.getName(new ItemStack(Items.COAL)).copy();
    }
}
