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
        MinionRecipeBuilder.miner("lapis", Tags.Items.GEMS_LAPIS, SkyblockRegistries.entities.lapisMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("iron", Tags.Items.INGOTS_IRON, SkyblockRegistries.entities.ironMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("gold", Tags.Items.INGOTS_GOLD, SkyblockRegistries.entities.goldMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("redstone", Tags.Items.DUSTS_REDSTONE, SkyblockRegistries.entities.redstoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("diamond", Tags.Items.GEMS_DIAMOND, SkyblockRegistries.entities.diamondMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("emerald", Tags.Items.GEMS_EMERALD, SkyblockRegistries.entities.emeraldMiner.get()).save(consumer);
    }

    @Override
    public String getName() {
        return name;
    }
}
