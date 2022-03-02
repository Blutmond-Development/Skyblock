package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.block.CompressedBlock;
import de.blutmondgilde.skyblock.block.EfficientCoalBlock;
import de.blutmondgilde.skyblock.block.NoEffectBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockBlocks extends AbstractSkyblockRegistry {
    private final DeferredRegister<Block> registry = create(ForgeRegistries.BLOCKS);
    public final RegistryObject<NoEffectBlock> mithrilBlock = registry.register("mithril_block",
        () -> new NoEffectBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(150F, 3600)));
    public final RegistryObject<NoEffectBlock> mithrilRawBlock = registry.register("raw_mithril_block",
        () -> new NoEffectBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(150F, 3600)));
    public final RegistryObject<NoEffectBlock> mithrilOre = registry.register("mithril_ore",
        () -> new NoEffectBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(150F, 3600)));
    public final RegistryObject<NoEffectBlock> mithrilDeepslateOre = registry.register("deepslate_mithril_ore",
        () -> new NoEffectBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(150F, 3600)));
    public final RegistryObject<NoEffectBlock> hardStone = registry.register("hard_stone",
        () -> new NoEffectBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_LIGHT_BLUE).strength(3F, 2400)));
    public final RegistryObject<EfficientCoalBlock> efficientCoalBlock = registry.register("efficient_coal_block", EfficientCoalBlock::new);
    public final RegistryObject<CompressedBlock> compressedCopperBlock = registry.register("compressed_copper_block", () -> new CompressedBlock(Blocks.COPPER_BLOCK));
    public final RegistryObject<CompressedBlock> compressedLapisBlock = registry.register("compressed_lapis_block", () -> new CompressedBlock(Blocks.LAPIS_BLOCK));
    public final RegistryObject<CompressedBlock> compressedIronBlock = registry.register("compressed_iron_block", () -> new CompressedBlock(Blocks.IRON_BLOCK));
    public final RegistryObject<CompressedBlock> compressedGoldBlock = registry.register("compressed_gold_block", () -> new CompressedBlock(Blocks.GOLD_BLOCK));
    public final RegistryObject<CompressedBlock> compressedRedstoneBlock = registry.register("compressed_redstone_block", () -> new CompressedBlock(Blocks.REDSTONE_BLOCK));
    public final RegistryObject<CompressedBlock> compressedDiamondBlock = registry.register("compressed_diamond_block", () -> new CompressedBlock(Blocks.DIAMOND_BLOCK));
    public final RegistryObject<CompressedBlock> compressedEmeraldBlock = registry.register("compressed_emerald_block", () -> new CompressedBlock(Blocks.EMERALD_BLOCK));
    public final RegistryObject<CompressedBlock> compressedCobblestone = registry.register("compressed_cobblestone", () -> new CompressedBlock(Blocks.COBBLESTONE));
    public final RegistryObject<CompressedBlock> compressedMithrilBlock = registry.register("compressed_mithril_block", () -> new CompressedBlock(mithrilBlock.get()));
    public final RegistryObject<CompressedBlock> compressedHardStone = registry.register("compressed_hard_stone", () -> new CompressedBlock(hardStone.get()));
    public final RegistryObject<CompressedBlock> compressedObsidian = registry.register("compressed_obsidian", () -> new CompressedBlock(Blocks.OBSIDIAN));
    public final RegistryObject<CompressedBlock> compressedGlowstoneBlock = registry.register("compressed_glowstone_block", () -> new CompressedBlock(Blocks.GLOWSTONE));
    public final RegistryObject<CompressedBlock> compressedGravel = registry.register("compressed_gravel", () -> new CompressedBlock(Blocks.GRAVEL));
    public final RegistryObject<CompressedBlock> compressedIce = registry.register("compressed_ice", () -> new CompressedBlock(Blocks.ICE));
    public final RegistryObject<CompressedBlock> compressedSand = registry.register("compressed_sand", () -> new CompressedBlock(Blocks.SAND));
    public final RegistryObject<CompressedBlock> compressedEndStone = registry.register("compressed_end_stone", () -> new CompressedBlock(Blocks.END_STONE));
    public final RegistryObject<CompressedBlock> compressedQuartzBlock = registry.register("compressed_quartz_block", () -> new CompressedBlock(Blocks.QUARTZ_BLOCK));
    public final RegistryObject<CompressedBlock> compressedHayBlock = registry.register("compressed_hay_block", () -> new CompressedBlock(Blocks.HAY_BLOCK));

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }

    public Iterable<Block> getKnownBlocks() {
        return registry.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
