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

public class EndStoneMinerEntity extends MinerEntity {
    public EndStoneMinerEntity(EntityType<EndStoneMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.END_STONE, 160);
            case 2 -> Map.of(Items.END_STONE, 320);
            case 3 -> Map.of(Items.END_STONE, 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 16);
            case 6 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 32);
            case 7 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 64);
            case 8 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 128);
            case 9 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 256);
            case 10 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 512);
            case 11 -> Map.of(SkyblockRegistries.items.compressedEndStone.get(), 1024, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> Tags.Blocks.END_STONES;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.AIR;
    }

    @Override
    protected MutableComponent getResultName() {
        return Items.END_STONE.getName(new ItemStack(Items.END_STONE)).copy();
    }
}
