package de.blutmondgilde.skyblock.event.handler;

import de.blutmondgilde.skyblock.data.recipe.SkyblockCraftingRecipeGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GatherDataEventHandler {
    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent e) {
        DataGenerator generator = e.getGenerator();
        generator.addProvider(new SkyblockCraftingRecipeGenerator(generator));
    }
}
