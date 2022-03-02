package de.blutmondgilde.skyblock.data.model;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockBlockStateGenerator extends BlockStateProvider {
    public SkyblockBlockStateGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Skyblock.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        cubeAll(SkyblockRegistries.blocks.hardStone);
        cubeAll(SkyblockRegistries.blocks.mithrilOre);
        cubeAll(SkyblockRegistries.blocks.mithrilDeepslateOre);
        cubeAll(SkyblockRegistries.blocks.mithrilBlock);
        cubeAll(SkyblockRegistries.blocks.mithrilRawBlock);

        compressedBlockModel(SkyblockRegistries.blocks.compressedMithrilBlock, SkyblockRegistries.blocks.mithrilBlock);
        compressedBlockModel(SkyblockRegistries.blocks.compressedHardStone, SkyblockRegistries.blocks.hardStone);

        compressedBlockModel(SkyblockRegistries.blocks.compressedCopperBlock, "copper_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedIronBlock, "iron_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedGoldBlock, "gold_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedRedstoneBlock, "redstone_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedDiamondBlock, "diamond_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedEmeraldBlock, "emerald_block");
        compressedBlockModel(SkyblockRegistries.blocks.efficientCoalBlock, "coal_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedCobblestone, "cobblestone");
        compressedBlockModel(SkyblockRegistries.blocks.compressedObsidian, "obsidian");
        compressedBlockModel(SkyblockRegistries.blocks.compressedGlowstoneBlock, "glowstone");
        compressedBlockModel(SkyblockRegistries.blocks.compressedGravel, "gravel");
        compressedBlockModel(SkyblockRegistries.blocks.compressedIce, "ice");
        compressedBlockModel(SkyblockRegistries.blocks.compressedEndStone, "end_stone");
        compressedBlockModel(SkyblockRegistries.blocks.compressedQuartzBlock, "quartz_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedLapisBlock, "lapis_block");
        compressedBlockModel(SkyblockRegistries.blocks.compressedSand, "sand");

        compressedBlockModel(SkyblockRegistries.blocks.compressedHayBlock, "hay_block");
    }

    private void compressedBlockModel(RegistryObject<? extends Block> block, ResourceLocation texture) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(new ModelFile.UncheckedModelFile(texture)).build());
        itemModels().getBuilder(block.get().getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(texture));
    }

    private void compressedBlockModel(RegistryObject<? extends Block> block, String fileName) {
        compressedBlockModel(block, mcLoc("block/" + fileName));
    }

    private void compressedBlockModel(RegistryObject<? extends Block> block, RegistryObject<? extends Block> parent) {
        compressedBlockModel(block, modLoc("block/" + parent.get().getRegistryName().getPath()));
    }

    private void cubeAll(RegistryObject<? extends Block> block) {
        getVariantBuilder(block.get()).forAllStates(state -> ConfiguredModel.builder().modelFile(cubeAll(block.get())).build());
        itemModels().getBuilder(block.get().getRegistryName().getPath()).parent(new ModelFile.UncheckedModelFile(new SkyblockResourceLocation("block/" + block.get().getRegistryName().getPath())));
    }
}
