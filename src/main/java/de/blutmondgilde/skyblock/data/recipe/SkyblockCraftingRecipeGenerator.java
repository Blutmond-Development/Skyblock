package de.blutmondgilde.skyblock.data.recipe;

import de.blutmondgilde.skyblock.registry.SkyblockRegistries;
import de.blutmondgilde.skyblock.registry.SkyblockTags;
import de.blutmondgilde.skyblock.util.SkyblockResourceLocation;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class SkyblockCraftingRecipeGenerator extends RecipeProvider {

    public SkyblockCraftingRecipeGenerator(DataGenerator pGenerator) {
        super(pGenerator);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //Miner Recipes
        MinionRecipeBuilder.miner("coal", ItemTags.COALS, SkyblockRegistries.entities.coalMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("cobblestone", Tags.Items.COBBLESTONE, SkyblockRegistries.entities.cobblestoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("copper", Tags.Items.INGOTS_COPPER, SkyblockRegistries.entities.copperMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("diamond", Tags.Items.GEMS_DIAMOND, SkyblockRegistries.entities.diamondMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("emerald", Tags.Items.GEMS_EMERALD, SkyblockRegistries.entities.emeraldMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("end_stone", Tags.Items.END_STONES, SkyblockRegistries.entities.endStoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("glowstone", Tags.Items.DUSTS_GLOWSTONE, SkyblockRegistries.entities.glowstoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("gold", Tags.Items.INGOTS_GOLD, SkyblockRegistries.entities.goldMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("gravel", Tags.Items.GRAVEL, SkyblockRegistries.entities.gravelMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("hard_stone", SkyblockTags.Items.HARD_STONE, SkyblockRegistries.entities.hardStoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("ice", SkyblockTags.Items.ICE, SkyblockRegistries.entities.iceMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("iron", Tags.Items.INGOTS_IRON, SkyblockRegistries.entities.ironMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("lapis", Tags.Items.GEMS_LAPIS, SkyblockRegistries.entities.lapisMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("mithril", SkyblockTags.Items.MITHRIL_INGOT, SkyblockRegistries.entities.mithrilMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("obsidian", Tags.Items.OBSIDIAN, SkyblockRegistries.entities.obsidianMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("quartz", Tags.Items.GEMS_QUARTZ, SkyblockRegistries.entities.quartzMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("redstone", Tags.Items.DUSTS_REDSTONE, SkyblockRegistries.entities.redstoneMiner.get()).save(consumer);
        MinionRecipeBuilder.miner("sand", Tags.Items.SAND, SkyblockRegistries.entities.sandMiner.get()).save(consumer);
        //Farmer Recipe
        MinionRecipeBuilder.farmer("wheat", Tags.Items.CROPS_WHEAT, SkyblockRegistries.entities.wheatFarmer.get()).save(consumer);
        //Ore Smelting Recipes
        rawToIngotSmelting(SkyblockTags.Items.MITHRIL_RAW, SkyblockRegistries.items.mithrilIngot.get(), consumer);
        rawToIngotSmelting(SkyblockTags.Items.MITHRIL_ORE, SkyblockRegistries.items.mithrilIngot.get(), consumer);
    }

    private void rawToIngotSmelting(Tag.Named<Item> tag, Item result, Consumer<FinishedRecipe> consumer) {
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(tag), result, 0.7F, 200)
            .unlockedBy("ingot_unlock", InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(tag).build()))
            .save(consumer, new SkyblockResourceLocation(tag.getName().getPath() + "_smelting"));
        SimpleCookingRecipeBuilder.blasting(Ingredient.of(tag), result, 0.7F, 100)
            .unlockedBy("ingot_unlock", InventoryChangeTrigger.TriggerInstance.hasItems(
                ItemPredicate.Builder.item().of(tag).build()))
            .save(consumer, new SkyblockResourceLocation(tag.getName().getPath() + "_blasting"));
    }
}
