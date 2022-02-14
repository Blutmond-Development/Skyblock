package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.block.CompressedBlock;
import de.blutmondgilde.skyblock.block.EfficientCoalBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockBlocks extends AbstractSkyblockRegistry {
    private final DeferredRegister<Block> registry = create(ForgeRegistries.BLOCKS);
    public final RegistryObject<EfficientCoalBlock> efficientCoalBlock = registry.register("efficient_coal_block", EfficientCoalBlock::new);
    public final RegistryObject<CompressedBlock> compressedCopperBlock = registry.register("compressed_copper_block", () -> new CompressedBlock(Blocks.COPPER_BLOCK));
    public final RegistryObject<CompressedBlock> compressedLapisLazuliBlock = registry.register("compressed_lapis_block", () -> new CompressedBlock(Blocks.LAPIS_BLOCK));
    public final RegistryObject<CompressedBlock> compressedIronBlock = registry.register("compressed_iron_block", () -> new CompressedBlock(Blocks.IRON_BLOCK));
    public final RegistryObject<CompressedBlock> compressedGoldBlock = registry.register("compressed_gold_block", () -> new CompressedBlock(Blocks.GOLD_BLOCK));
    public final RegistryObject<CompressedBlock> compressedRedstoneBlock = registry.register("compressed_redstone_block", () -> new CompressedBlock(Blocks.REDSTONE_BLOCK));
    public final RegistryObject<CompressedBlock> compressedDiamondBlock = registry.register("compressed_diamond_block", () -> new CompressedBlock(Blocks.DIAMOND_BLOCK));
    public final RegistryObject<CompressedBlock> compressedEmeraldBlock = registry.register("compressed_emerald_block", () -> new CompressedBlock(Blocks.EMERALD_BLOCK));

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
