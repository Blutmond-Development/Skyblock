package de.blutmondgilde.skyblock.data.recipe;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SkyblockCraftingRecipeGenerator extends RecipeProvider {
    private static final String name = "skyblock:recipe";

    public SkyblockCraftingRecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        MinionRecipeBuilder.miner("coal", ItemTags.COALS, SkyblockRegistries.entities.coalMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("copper", Tags.Items.INGOTS_COPPER, SkyblockRegistries.entities.copperMiner.get()).save(consumer);
    }

    @Override
    public String getName() {
        return name;
    }
}
