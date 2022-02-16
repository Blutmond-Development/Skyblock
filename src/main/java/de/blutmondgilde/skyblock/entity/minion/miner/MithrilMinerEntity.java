package de.blutmondgilde.skyblock.entity.minion.miner;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.function.Supplier;

public class MithrilMinerEntity extends MinerEntity {
    public MithrilMinerEntity(EntityType<MithrilMinerEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public Map<Item, Integer> getItemsForLevelUp() {
        if (getMinionLevel() == 12) {
            return Map.of();
        }

        return switch (getMinionLevel()) {
            case 1 -> Map.of(SkyblockRegistries.items.mithrilIngot.get(), 160);
            case 2 -> Map.of(SkyblockRegistries.items.mithrilIngot.get(), 320);
            case 3 -> Map.of(SkyblockRegistries.items.mithrilIngot.get(), 512);
            case 4 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 8);
            case 5 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 24);
            case 6 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 64);
            case 7 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 128);
            case 8 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 256);
            case 9 -> Map.of(SkyblockRegistries.items.compressedMithril.get(), 512);
            case 10 -> Map.of(SkyblockRegistries.items.compressedMithrilBlock.get(), 8);
            case 11 -> Map.of(SkyblockRegistries.items.compressedMithrilBlock.get(), 16, SkyblockRegistries.items.instructionsOfTheMaster.get(), 1);
            default -> Map.of();
        };
    }

    @Override
    public Supplier<Tags.IOptionalNamedTag<Block>> getMiningTarget() {
        return () -> SkyblockTags.Blocks.MITHRIL_ORE;
    }

    @Override
    public Supplier<Block> getReplacementBlock() {
        return () -> Blocks.COBBLESTONE;
    }

    @Override
    protected MutableComponent getResultName() {
        return SkyblockRegistries.items.mithrilOre.get().getName(new ItemStack(SkyblockRegistries.items.mithrilOre.get())).copy();
    }
}
