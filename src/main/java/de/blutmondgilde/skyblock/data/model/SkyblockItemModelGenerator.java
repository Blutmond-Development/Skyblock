package de.blutmondgilde.skyblock.data.model;

import de.blutmondgilde.skyblock.Skyblock;
import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class SkyblockItemModelGenerator extends ItemModelProvider {
    public SkyblockItemModelGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Skyblock.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Items
        singleTexture(SkyblockRegistries.items.mithrilIngot);
        singleTexture(SkyblockRegistries.items.mithrilRaw);
        singleTexture(SkyblockRegistries.items.instructionsOfTheMaster);

        compressedItemModel(SkyblockRegistries.items.compressedMithril, SkyblockRegistries.items.mithrilIngot);
        //Compressed Items
        compressedItemModel(SkyblockRegistries.items.efficientCharCoalItem, "charcoal");
        compressedItemModel(SkyblockRegistries.items.efficientCoalItem, "coal");
        compressedItemModel(SkyblockRegistries.items.tastyBreadItem, "bread");
        compressedItemModel(SkyblockRegistries.items.compressedCopperIngot, "copper_ingot");
        compressedItemModel(SkyblockRegistries.items.compressedLapisLazuli, "lapis_lazuli");
        compressedItemModel(SkyblockRegistries.items.compressedIronIngot, "iron_ingot");
        compressedItemModel(SkyblockRegistries.items.compressedGoldIngot, "gold_ingot");
        compressedItemModel(SkyblockRegistries.items.compressedRedstone, "redstone");
        compressedItemModel(SkyblockRegistries.items.compressedDiamond, "diamond");
        compressedItemModel(SkyblockRegistries.items.compressedEmerald, "emerald");
        compressedItemModel(SkyblockRegistries.items.compressedQuartz, "quartz");
        compressedItemModel(SkyblockRegistries.items.compressedGlowstone, "glowstone_dust");
    }

    private void singleTexture(RegistryObject<? extends Item> item) {
        getBuilder(item.get().getRegistryName().getPath())
            .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
            .texture("layer0", modLoc("item/" + item.get().getRegistryName().getPath()));
    }

    private void compressedItemModel(RegistryObject<? extends Item> item, ResourceLocation texture) {
        getBuilder(item.get().getRegistryName().getPath())
            .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
            .texture("layer0", texture);
    }

    private void compressedItemModel(RegistryObject<? extends Item> item, String fileName) {
        compressedItemModel(item, mcLoc("item/" + fileName));
    }

    private void compressedItemModel(RegistryObject<? extends Item> item, RegistryObject<? extends Item> parent) {
        getBuilder(item.get().getRegistryName().getPath())
            .parent(new ModelFile.UncheckedModelFile(mcLoc("item/generated")))
            .texture("layer0", modLoc("item/" + parent.get().getRegistryName().getPath()));
    }
}
