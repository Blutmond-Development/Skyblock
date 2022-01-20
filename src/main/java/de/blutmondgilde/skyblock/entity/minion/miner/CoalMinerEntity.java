package de.blutmondgilde.skyblock.entity.minion.miner;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Supplier;

public class CoalMinerEntity extends MinerEntity {
    public CoalMinerEntity(EntityType<MinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Supplier<Block> getMiningTarget() {
        return () -> Blocks.COAL_ORE;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.COBBLESTONE;
    }
}
