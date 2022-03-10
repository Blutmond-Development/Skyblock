package de.blutmondgilde.skyblock.entity.minion.miner;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Supplier;

public class SandMinerEntity extends MinerEntity {
    public SandMinerEntity(EntityType<SandMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.SAND, 160);
            case 2 -> Map.of(Items.SAND, 320);
            case 3 -> Map.of(Items.SAND, 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 16);
            case 6 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 32);
            case 7 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 64);
            case 8 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 128);
            case 9 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 256);
            case 10 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 512);
            case 11 -> Map.of(SkyblockRegistries.items.compressedSand.get(), 1024, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> Tags.Blocks.SAND;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.SANDSTONE;
    }

    @Override
    protected MutableComponent getResultName() {
        return new ItemStack(Items.SAND).getDisplayName().copy();
    }
}
