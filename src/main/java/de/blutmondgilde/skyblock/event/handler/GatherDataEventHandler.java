package de.blutmondgilde.skyblock.event.handler;

import de.blutmondgilde.skyblock.data.drop.SkyblockLootTableGenerator;
import de.blutmondgilde.skyblock.data.model.SkyblockBlockStateGenerator;
import de.blutmondgilde.skyblock.data.model.SkyblockItemModelGenerator;
import de.blutmondgilde.skyblock.data.recipe.SkyblockCraftingRecipeGenerator;
import de.blutmondgilde.skyblock.data.tag.SkyblockBlockTagGenerator;
import de.blutmondgilde.skyblock.data.tag.SkyblockItemTagGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GatherDataEventHandler {
    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        //Recipe
        generator.addProvider(new SkyblockCraftingRecipeGenerator(generator));
        //Block Tags
        SkyblockBlockTagGenerator blockTagGenerator = new SkyblockBlockTagGenerator(generator, e.getExistingFileHelper());
        generator.addProvider(blockTagGenerator);
        //Item Tags
        generator.addProvider(new SkyblockItemTagGenerator(generator, blockTagGenerator, e.getExistingFileHelper()));
        //Item Models
        generator.addProvider(new SkyblockItemModelGenerator(generator, e.getExistingFileHelper()));
        //Block Models
        generator.addProvider(new SkyblockBlockStateGenerator(generator, e.getExistingFileHelper()));
        //Loot Tables
        generator.addProvider(new SkyblockLootTableGenerator(generator));
    }
}
