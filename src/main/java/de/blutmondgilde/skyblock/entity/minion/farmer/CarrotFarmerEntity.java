package de.blutmondgilde.skyblock.entity.minion.farmer;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Predicate;

public class CarrotFarmerEntity extends FarmerEntity {
    public CarrotFarmerEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.CARROT, 128);
            case 2 -> Map.of(Items.CARROT, 256);
            case 3 -> Map.of(Items.CARROT, 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 24);
            case 6 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 64);
            case 7 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 128);
            case 8 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 256);
            case 9 -> Map.of(SkyblockRegistries.items.compressedCarrot.get(), 512);
            case 10 -> Map.of(SkyblockRegistries.items.compressedGoldenCarrot.get(), 8);
            case 11 -> Map.of(SkyblockRegistries.items.compressedGoldenCarrot.get(), 16, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    protected MutableComponent getResultName() {
        return Items.CARROT.getName(new ItemStack(Items.CARROT)).copy();
    }

    @Override
    public Predicate<BlockState> IsCorrectBlock() {
        return state -> {
            if (!state.getBlock().equals(Blocks.CARROTS)) return false;
            if (!state.hasProperty(CropBlock.AGE)) return false;
            //noinspection RedundantIfStatement
            if (state.getValue(CropBlock.AGE) != CropBlock.MAX_AGE) return false;
            return true;
        };
    }

    @Override
    public BlockState getReplacementBlock() {
        return Blocks.CARROTS.defaultBlockState();
    }
}
