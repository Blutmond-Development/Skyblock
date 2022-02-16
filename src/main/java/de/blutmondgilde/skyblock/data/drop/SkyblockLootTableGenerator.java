package de.blutmondgilde.skyblock.data.drop;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SkyblockLootTableGenerator extends LootTableProvider {
    public SkyblockLootTableGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return ImmutableList.of(
            Pair.of(Blocks::new, LootContextParamSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationtracker) {
        map.forEach((resourceLocation, lootTable) -> LootTables.validate(validationtracker, resourceLocation, lootTable));
    }

    protected static class Blocks extends BlockLoot {
        @Override
        protected void addTables() {
            dropSelf(SkyblockRegistries.blocks.compressedCopperBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedIronBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedGoldBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedLapisBlock.get());
            dropSelf(SkyblockRegistries.blocks.efficientCoalBlock.get());
            dropSelf(SkyblockRegistries.blocks.hardStone.get());
            dropSelf(SkyblockRegistries.blocks.compressedCobblestone.get());
            dropSelf(SkyblockRegistries.blocks.compressedRedstoneBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedDiamondBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedEmeraldBlock.get());
            dropSelf(SkyblockRegistries.blocks.mithrilBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedMithrilBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedHardStone.get());
            dropSelf(SkyblockRegistries.blocks.compressedObsidian.get());
            dropSelf(SkyblockRegistries.blocks.compressedGlowstoneBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedGravel.get());
            dropSelf(SkyblockRegistries.blocks.compressedIce.get());
            dropSelf(SkyblockRegistries.blocks.compressedSand.get());
            dropSelf(SkyblockRegistries.blocks.compressedEndStone.get());
            dropSelf(SkyblockRegistries.blocks.mithrilRawBlock.get());
            dropSelf(SkyblockRegistries.blocks.compressedQuartzBlock.get());

            oreDrop(SkyblockRegistries.blocks.mithrilOre, SkyblockRegistries.items.mithrilRaw);
            oreDrop(SkyblockRegistries.blocks.mithrilDeepslateOre, SkyblockRegistries.items.mithrilRaw);
        }

        private void oreDrop(RegistryObject<? extends Block> block, RegistryObject<? extends Item> item) {
            add(block.get(), createOreDrop(block.get(), item.get()));
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return SkyblockRegistries.blocks.getKnownBlocks();
        }
    }
}
