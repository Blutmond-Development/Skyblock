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

public class CoalMinerEntity extends MinerEntity {
    public CoalMinerEntity(EntityType<CoalMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.COAL, 160);
            case 2 -> Map.of(Items.COAL, 320);
            case 3 -> Map.of(Items.COAL, 512);
            case 4 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 24);
            case 6 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 64);
            case 7 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 128);
            case 8 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 256);
            case 9 -> Map.of(SkyblockRegistries.items.efficientCoalItem.get(), 512);
            case 10 -> Map.of(SkyblockRegistries.items.efficientCoalBlock.get(), 8);
            case 11 -> Map.of(SkyblockRegistries.items.efficientCoalBlock.get(), 16, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
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
