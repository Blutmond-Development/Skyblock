package de.blutmondgilde.skyblock.entity.minion.miner;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
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

public class IceMinerEntity extends MinerEntity {
    public IceMinerEntity(EntityType<IceMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(Items.ICE, 160);
            case 2 -> Map.of(Items.ICE, 320);
            case 3 -> Map.of(Items.ICE, 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 16);
            case 6 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 32);
            case 7 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 64);
            case 8 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 128);
            case 9 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 256);
            case 10 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 512);
            case 11 -> Map.of(SkyblockRegistries.items.compressedIce.get(), 1024, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> SkyblockTags.Blocks.ICE;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.COBBLESTONE;
    }

    @Override
    protected MutableComponent getResultName() {
        return Items.ICE.getName(new ItemStack(Items.ICE)).copy();
    }
}
