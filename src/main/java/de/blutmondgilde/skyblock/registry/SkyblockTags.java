package de.blutmondgilde.skyblock.registry;

import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class SkyblockTags {
    public static class Items {
        //Items
        public static final Tag.Named<Item> MINION_SPECIAL_UPGRADES = ItemTags.createOptional(new SkyblockResourceLocation("minion_special_upgrades"));
        public static final Tag.Named<Item> MINION_UPGRADES = ItemTags.createOptional(new SkyblockResourceLocation("minion_upgrades"));
        public static final Tag.Named<Item> MITHRIL_INGOT = ItemTags.createOptional(new SkyblockResourceLocation("ingot/mithril"));
        public static final Tag.Named<Item> MITHRIL_RAW = ItemTags.createOptional(new SkyblockResourceLocation("raw_materials/mithril"));
        //Blocks
        public static final Tag.Named<Item> HARD_STONE = ItemTags.createOptional(new SkyblockResourceLocation("hard_stone"));
        public static final Tag.Named<Item> GLOWSTONE_ORE = ItemTags.createOptional(new SkyblockResourceLocation("ores/glowstone"));
        public static final Tag.Named<Item> MITHRIL_ORE = ItemTags.createOptional(new SkyblockResourceLocation("ores/mithril"));
        public static final Tag.Named<Item> ICE = ItemTags.createOptional(new SkyblockResourceLocation("ice"));
        public static final Tag.Named<Item> MITHRIL_BLOCK = ItemTags.createOptional(new SkyblockResourceLocation("storage_blocks/mithril"));
        public static final Tag.Named<Item> MITHRIL_RAW_BLOCK = ItemTags.createOptional(new SkyblockResourceLocation("storage_blocks/raw_mithril"));
    }

    public static class Blocks {
        public static final Tags.IOptionalNamedTag<Block> HARD_STONE = BlockTags.createOptional(new SkyblockResourceLocation("hard_stone"));
        public static final Tags.IOptionalNamedTag<Block> MITHRIL_ORE = BlockTags.createOptional(new SkyblockResourceLocation("ores/mithril"));
        public static final Tags.IOptionalNamedTag<Block> MITHRIL_BLOCK = BlockTags.createOptional(new SkyblockResourceLocation("storage_blocks/mithril"));
        public static final Tags.IOptionalNamedTag<Block> MITHRIL_RAW_BLOCK = BlockTags.createOptional(new SkyblockResourceLocation("storage_blocks/raw_mithril"));
        public static final Tags.IOptionalNamedTag<Block> GLOWSTONE_ORE = BlockTags.createOptional(new SkyblockResourceLocation("ores/glowstone"));
        public static final Tags.IOptionalNamedTag<Block> ICE = BlockTags.createOptional(new SkyblockResourceLocation("ice"));
    }
}
