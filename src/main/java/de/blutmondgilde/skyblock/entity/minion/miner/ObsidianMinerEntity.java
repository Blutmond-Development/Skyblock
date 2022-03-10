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

public class ObsidianMinerEntity extends MinerEntity {
    public ObsidianMinerEntity(EntityType<ObsidianMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.OBSIDIAN, 160);
            case 2 -> Map.of(Items.OBSIDIAN, 320);
            case 3 -> Map.of(Items.OBSIDIAN, 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 16);
            case 6 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 32);
            case 7 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 64);
            case 8 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 128);
            case 9 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 256);
            case 10 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 512);
            case 11 -> Map.of(SkyblockRegistries.items.compressedObsidian.get(), 1024, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> Tags.Blocks.OBSIDIAN;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.COBBLESTONE;
    }

    @Override
    protected MutableComponent getResultName() {
        return Items.OBSIDIAN.getName(new ItemStack(Items.OBSIDIAN)).copy();
    }
}
