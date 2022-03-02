package de.blutmondgilde.skyblock.entity.minion.farmer;

import de.blutmondgilde.skyblock.entity.minion.MinionEntity;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;
import java.util.function.Predicate;

public class WheatFarmerEntity extends FarmerEntity {
    public WheatFarmerEntity(EntityType<? extends MinionEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.WHEAT, 80);
            case 2 -> Map.of(Items.WHEAT, 160);
            case 3 -> Map.of(Items.WHEAT, 320);
            case 4 -> Map.of(Items.WHEAT, 512);
            case 5 -> Map.of(Items.HAY_BLOCK, 96);
            case 6 -> Map.of(Items.HAY_BLOCK, 192);
            case 7 -> Map.of(Items.HAY_BLOCK, 384);
            case 8 -> Map.of(Items.HAY_BLOCK, 512);
            case 9 -> Map.of(SkyblockRegistries.items.compressedHayBlock.get(), 8);
            case 10 -> Map.of(SkyblockRegistries.items.compressedHayBlock.get(), 16);
            case 11 -> Map.of(SkyblockRegistries.items.compressedHayBlock.get(), 32, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    protected MutableComponent getResultName() {
        return null;
    }

    @Override
    public Predicate<BlockState> IsCorrectBlock() {
        return null;
    }

    @Override
    public BlockState getReplacementBlock() {
        return null;
    }
}
