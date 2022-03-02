package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.item.CompressedItem;
import de.blutmondgilde.skyblock.item.InstructionsOfTheMasterItem;
import de.blutmondgilde.skyblock.item.NoEffectBlockItem;
import de.blutmondgilde.skyblock.item.NoEffectItem;
import de.blutmondgilde.skyblock.item.PickedMinionItem;
import de.blutmondgilde.skyblock.item.block.CompressedBlockItem;
import de.blutmondgilde.skyblock.item.block.EfficientCoalBlockItem;
import de.blutmondgilde.skyblock.item.fuel.EfficientCharCoalItem;
import de.blutmondgilde.skyblock.item.fuel.EfficientCoalItem;
import de.blutmondgilde.skyblock.item.fuel.TastyBreadItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockItems extends AbstractSkyblockRegistry {
    private final DeferredRegister<Item> registry = create(ForgeRegistries.ITEMS);
    public final RegistryObject<EfficientCharCoalItem> efficientCharCoalItem = registry.register("efficient_char_coal", EfficientCharCoalItem::new);
    public final RegistryObject<EfficientCoalItem> efficientCoalItem = registry.register("efficient_coal", EfficientCoalItem::new);
    public final RegistryObject<TastyBreadItem> tastyBreadItem = registry.register("tasty_bread", TastyBreadItem::new);
    public final RegistryObject<EfficientCoalBlockItem> efficientCoalBlock = registry.register("efficient_coal_block", EfficientCoalBlockItem::new);
    public final RegistryObject<InstructionsOfTheMasterItem> instructionsOfTheMaster = registry.register("instructions_of_the_master", InstructionsOfTheMasterItem::new);
    public final RegistryObject<PickedMinionItem> pickedMinion = registry.register("picked_minion", PickedMinionItem::new);
    public final RegistryObject<CompressedItem> compressedCopperIngot = registry.register("compressed_copper_ingot", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedLapisLazuli = registry.register("compressed_lapis_lazuli", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedIronIngot = registry.register("compressed_iron_ingot", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedGoldIngot = registry.register("compressed_gold_ingot", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedRedstone = registry.register("compressed_redstone", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedDiamond = registry.register("compressed_diamond", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedEmerald = registry.register("compressed_emerald", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedQuartz = registry.register("compressed_quartz", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedMithril = registry.register("compressed_mithril", CompressedItem::new);
    public final RegistryObject<CompressedItem> compressedGlowstone = registry.register("compressed_glowstone", CompressedItem::new);
    public final RegistryObject<NoEffectItem> mithrilRaw = registry.register("raw_mithril", NoEffectItem::new);
    public final RegistryObject<NoEffectItem> mithrilIngot = registry.register("mithril_ingot", NoEffectItem::new);

    public final RegistryObject<NoEffectBlockItem> mithrilBlock =
        registry.register("mithril_block", () -> new NoEffectBlockItem(SkyblockRegistries.blocks.mithrilBlock));
    public final RegistryObject<NoEffectBlockItem> mithrilOre =
        registry.register("mithril_ore", () -> new NoEffectBlockItem(SkyblockRegistries.blocks.mithrilOre));
    public final RegistryObject<NoEffectBlockItem> mithrilDeepslateOre =
        registry.register("deepslate_mithril_ore", () -> new NoEffectBlockItem(SkyblockRegistries.blocks.mithrilDeepslateOre));
    public final RegistryObject<NoEffectBlockItem> hardStone =
        registry.register("hard_stone", () -> new NoEffectBlockItem(SkyblockRegistries.blocks.hardStone));
    public final RegistryObject<NoEffectBlockItem> mithrilRawBlock =
        registry.register("raw_mithril_block", () -> new NoEffectBlockItem(SkyblockRegistries.blocks.mithrilRawBlock));

    public final RegistryObject<CompressedBlockItem> compressedCopperBlock =
        registry.register("compressed_copper_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedCopperBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedLapisBlock =
        registry.register("compressed_lapis_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedLapisBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedIronBlock =
        registry.register("compressed_iron_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedIronBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedGoldBlock =
        registry.register("compressed_gold_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedGoldBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedRedstoneBlock =
        registry.register("compressed_redstone_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedRedstoneBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedDiamondBlock =
        registry.register("compressed_diamond_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedDiamondBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedEmeraldBlock =
        registry.register("compressed_emerald_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedEmeraldBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedCobblestoneBlock =
        registry.register("compressed_cobblestone", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedCobblestone.get()));
    public final RegistryObject<CompressedBlockItem> compressedMithrilBlock =
        registry.register("compressed_mithril_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedMithrilBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedHardStone =
        registry.register("compressed_hard_stone", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedHardStone.get()));
    public final RegistryObject<CompressedBlockItem> compressedObsidian =
        registry.register("compressed_obsidian", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedObsidian.get()));
    public final RegistryObject<CompressedBlockItem> compressedGlowstoneBlock =
        registry.register("compressed_glowstone_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedGlowstoneBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedGravel =
        registry.register("compressed_gravel", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedGravel.get()));
    public final RegistryObject<CompressedBlockItem> compressedIce =
        registry.register("compressed_ice", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedIce.get()));
    public final RegistryObject<CompressedBlockItem> compressedSand =
        registry.register("compressed_sand", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedSand.get()));
    public final RegistryObject<CompressedBlockItem> compressedEndStone =
        registry.register("compressed_end_stone", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedEndStone.get()));
    public final RegistryObject<CompressedBlockItem> compressedQuartzBlock =
        registry.register("compressed_quartz_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedQuartzBlock.get()));
    public final RegistryObject<CompressedBlockItem> compressedHayBlock =
        registry.register("compressed_hay_block", () -> new CompressedBlockItem(SkyblockRegistries.blocks.compressedHayBlock.get()));

    @Override
    void register(IEventBus modBus) {
        registry.register(modBus);
    }
}
