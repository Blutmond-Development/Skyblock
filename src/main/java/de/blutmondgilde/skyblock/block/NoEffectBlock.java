package de.blutmondgilde.skyblock.block;

import net.minecraft.world.level.block.Block;

public class NoEffectBlock extends Block {
    public NoEffectBlock(Properties properties) {
        super(properties.requiresCorrectToolForDrops());
    }
}
