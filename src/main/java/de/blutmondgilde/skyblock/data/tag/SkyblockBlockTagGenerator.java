package de.blutmondgilde.skyblock.data.tag;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class SkyblockBlockTagGenerator extends BlockTagsProvider {

    public SkyblockBlockTagGenerator(DataGenerator pGenerator, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator, Skyblock.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        //Blocks
        tag(SkyblockTags.Blocks.GLOWSTONE_ORE).add(Blocks.GLOWSTONE);
        tag(SkyblockTags.Blocks.ICE).add(Blocks.ICE);
        tag(SkyblockTags.Blocks.HARD_STONE).add(SkyblockRegistries.blocks.hardStone.get());
        tag(SkyblockTags.Blocks.MITHRIL_ORE).add(SkyblockRegistries.blocks.mithrilOre.get(), SkyblockRegistries.blocks.mithrilDeepslateOre.get());
        tag(SkyblockTags.Blocks.MITHRIL_BLOCK).add(SkyblockRegistries.blocks.mithrilBlock.get());
        tag(SkyblockTags.Blocks.MITHRIL_RAW_BLOCK).add(SkyblockRegistries.blocks.mithrilRawBlock.get());
        //Tags
        tag(Tags.Blocks.ORES).addTags(SkyblockTags.Blocks.MITHRIL_ORE);
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(SkyblockTags.Blocks.MITHRIL_BLOCK, SkyblockTags.Blocks.MITHRIL_RAW_BLOCK);
    }
}
