package de.blutmondgilde.skyblock.data.tag;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class SkyblockItemTagGenerator extends ItemTagsProvider {

    public SkyblockItemTagGenerator(DataGenerator pGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, blockTagProvider, Skyblock.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Items
        tag(SkyblockTags.Items.MINION_UPGRADES);
        tag(SkyblockTags.Items.MINION_SPECIAL_UPGRADES);
        tag(SkyblockTags.Items.MITHRIL_INGOT).add(SkyblockRegistries.items.mithrilIngot.get());
        tag(SkyblockTags.Items.MITHRIL_RAW).add(SkyblockRegistries.items.mithrilRaw.get());
        //Blocks
        tag(SkyblockTags.Items.GLOWSTONE_ORE).add(Items.GLOWSTONE);
        tag(SkyblockTags.Items.ICE).add(Items.ICE);
        tag(SkyblockTags.Items.HARD_STONE).add(SkyblockRegistries.items.hardStone.get());
        tag(SkyblockTags.Items.MITHRIL_ORE).add(SkyblockRegistries.items.mithrilOre.get(), SkyblockRegistries.items.mithrilDeepslateOre.get());
        tag(SkyblockTags.Items.MITHRIL_BLOCK).add(SkyblockRegistries.items.mithrilBlock.get());
        tag(SkyblockTags.Items.MITHRIL_RAW_BLOCK).add(SkyblockRegistries.items.mithrilRawBlock.get());
        //Tags
        tag(Tags.Items.INGOTS).addTags(SkyblockTags.Items.MITHRIL_INGOT);
        tag(Tags.Items.RAW_MATERIALS).addTags(SkyblockTags.Items.MITHRIL_RAW);
        tag(Tags.Items.ORES).addTags(SkyblockTags.Items.MITHRIL_ORE);
        tag(Tags.Items.STORAGE_BLOCKS).addTags(SkyblockTags.Items.MITHRIL_BLOCK, SkyblockTags.Items.MITHRIL_RAW_BLOCK);

        tag(SkyblockTags.Items.TILLABLE).add(Items.DIRT, Items.GRASS_BLOCK);
    }
}
