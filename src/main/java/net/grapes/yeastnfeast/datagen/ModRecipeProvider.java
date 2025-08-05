package net.grapes.yeastnfeast.datagen;

import net.grapes.yeastnfeast.block.ModBlocks;
import net.grapes.yeastnfeast.item.ModItems;
import net.grapes.yeastnfeast.util.ModTags;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        // Shaped Recipes for Food Items
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TANKARD.get())
                .pattern("S S")
                .pattern("PSP")
                .define('S', ItemTags.PLANKS)
                .define('P', Items.IRON_NUGGET)
                .unlockedBy("has_iron_nugget", inventoryTrigger(ItemPredicate.Builder.item().of(Items.IRON_NUGGET).build()))
                .save(recipeOutput);ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.TANKARD.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ELDERBERRY_PIE.get())
                .pattern(" S ")
                .pattern("STS")
                .pattern("PHP")
                .define('P', ModTags.Items.CROPS_GRAIN)
                .define('S', ModItems.ELDERBERRIES.get())
                .define('T', Items.SUGAR)
                .define('H', ModTags.Items.FOODS_MILK)
                .unlockedBy("has_elderberries", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.ELDERBERRIES.get()).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.ROSE_TART.get())
                .pattern(" S ")
                .pattern("STS")
                .pattern("PHP")
                .define('P', ModTags.Items.CROPS_GRAIN)
                .define('S', ModItems.ROSE_HIPS.get())
                .define('T', Items.SUGAR)
                .define('H', ModTags.Items.FOODS_MILK)
                .unlockedBy("has_rose_hips", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.ROSE_HIPS.get()).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.APPLE_PIE.get())
                .pattern(" S ")
                .pattern("STS")
                .pattern("PHP")
                .define('P', ModTags.Items.CROPS_GRAIN)
                .define('S', Items.APPLE)
                .define('T', Items.SUGAR)
                .define('H', ModTags.Items.FOODS_MILK)
                .unlockedBy("has_apple", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.APPLE).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.BERRY_ROLL.get())
                .pattern(" S ")
                .pattern("SPS")
                .pattern("PHP")
                .define('P', ModTags.Items.CROPS_GRAIN)
                .define('S', ModItems.HAWTHORN_BERRIES.get())
                .define('H', ModTags.Items.FOODS_MILK)
                .unlockedBy("has_hawthorn_berries", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.HAWTHORN_BERRIES.get()).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.TREE_TAP.get())
                .pattern(" H")
                .pattern("PS")
                .define('P', Items.STICK)
                .define('S', ItemTags.PLANKS)
                .define('H', Items.IRON_NUGGET)
                .unlockedBy("has_hawthorn_berries", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.HAWTHORN_BERRIES.get()).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.KEG.get())
                .pattern("PSP")
                .define('P', Items.STICK)
                .define('S', Blocks.BARREL)
                .unlockedBy("has_barrel", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.BARREL).build()))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.JAR.get(), 2)
                .pattern("PSP")
                .pattern(" P ")
                .define('P', Blocks.GLASS)
                .define('S', ItemTags.PLANKS)
                .unlockedBy("has_glass", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Blocks.GLASS).build()))
                .save(recipeOutput);

        // Shapeless Recipe
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.YEAST.get())
                .requires(ModTags.Items.MUSHROOMS)
                .requires(ModTags.Items.MUSHROOMS)
                .unlockedBy("has_mushroom", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModTags.Items.MUSHROOMS).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MILK_BOTTLE.get(), 4)
                .requires(Items.MILK_BUCKET)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.GLASS_BOTTLE)
                .unlockedBy("has_milk_bucket", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.MILK_BUCKET).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.MILK_BUCKET)
                .requires(Items.BUCKET)
                .requires(ModItems.MILK_BOTTLE.get())
                .requires(ModItems.MILK_BOTTLE.get())
                .requires(ModItems.MILK_BOTTLE.get())
                .requires(ModItems.MILK_BOTTLE.get())
                .unlockedBy("has_milk_bottle", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.MILK_BOTTLE.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SWEET_PORRIDGE.get())
                .requires(Items.BOWL)
                .requires(ModItems.MOLASSES.get())
                .requires(ModTags.Items.FOODS_MILK)
                .requires(Items.WHEAT)
                .requires(ModTags.Items.FOODS_BERRY)
                .unlockedBy("has_molasses", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.MOLASSES.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SPICED_PORRIDGE.get())
                .requires(Items.BOWL)
                .requires(ModItems.GARLIC.get())
                .requires(ModTags.Items.FOODS_MILK)
                .requires(ModItems.RYE.get())
                .requires(ModItems.GINGER.get())
                .unlockedBy("has_rye", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RYE.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BARLEY_AND_BEEF_STEW.get())
                .requires(Items.BOWL)
                .requires(ModItems.BARLEY.get())
                .requires(ModTags.Items.FOODS_VEGETABLE)
                .requires(ModTags.Items.FOODS_VEGETABLE)
                .requires(ModTags.Items.COOKED_BEEF)
                .unlockedBy("has_cooked_beef", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_BEEF).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MEAD_BRAISED_PORK.get())
                .requires(Items.BOWL)
                .requires(ModItems.HONEY_MEAD.get())
                .requires(ModTags.Items.FOODS_VEGETABLE)
                .requires(ModItems.BARLEY.get())
                .requires(ModTags.Items.COOKED_PORK)
                .unlockedBy("has_cooked_porkchop", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_PORKCHOP).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HERBAL_COD.get())
                .requires(Items.BOWL)
                .requires(ModItems.MINT.get())
                .requires(ModItems.GINGER.get())
                .requires(ModItems.LEMON.get())
                .requires(ModTags.Items.COOKED_COD)
                .unlockedBy("has_cooked_cod", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_COD).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.LEMON_GLAZED_CHICKEN.get())
                .requires(Items.BOWL)
                .requires(Items.HONEY_BOTTLE)
                .requires(ModItems.GARLIC.get())
                .requires(ModItems.LEMON.get())
                .requires(ModTags.Items.COOKED_CHICKEN)
                .unlockedBy("has_cooked_chicken", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_CHICKEN).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.FORAGER_FEAST.get())
                .requires(Items.BOWL)
                .requires(ModItems.RYE.get())
                .requires(ModItems.MINT.get())
                .requires(ModItems.ELDERBERRIES.get())
                .requires(ModTags.Items.COOKED_MUTTON)
                .unlockedBy("has_cooked_mutton", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_MUTTON).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MAPLE_GLAZED_RABBIT.get())
                .requires(Items.BOWL)
                .requires(ModItems.MAPLE_SYRUP.get())
                .requires(ModItems.BARLEY.get())
                .requires(Items.BEETROOT)
                .requires(Items.COOKED_RABBIT)
                .unlockedBy("has_cooked_rabbit", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_RABBIT).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.SALMON_CHOWDER.get())
                .requires(Items.BOWL)
                .requires(ModItems.LEMON.get())
                .requires(ModTags.Items.FOODS_MILK)
                .requires(Items.KELP)
                .requires(ModTags.Items.COOKED_SALMON)
                .unlockedBy("has_cooked_salmon", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.COOKED_SALMON).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BARLEY_BREAD.get())
                .requires(ModItems.BARLEY.get())
                .requires(ModItems.BARLEY.get())
                .requires(ModTags.Items.FOODS_MILK)
                .requires(ModItems.YEAST.get())
                .unlockedBy("has_barley", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.BARLEY.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RYE_BREAD.get())
                .requires(ModItems.RYE.get())
                .requires(ModItems.RYE.get())
                .requires(ModTags.Items.FOODS_MILK)
                .requires(ModItems.YEAST.get())
                .unlockedBy("has_rye", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RYE.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOLASSES_BREAD.get())
                .requires(ModTags.Items.CROPS_GRAIN)
                .requires(ModTags.Items.CROPS_GRAIN)
                .requires(ModItems.MOLASSES.get())
                .requires(ModItems.YEAST.get())
                .unlockedBy("has_molasses", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RYE.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RYE_SEEDS.get())
                .requires(ModItems.RYE.get())
                .unlockedBy("has_rye", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.RYE.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BARLEY_SEEDS.get())
                .requires(ModItems.BARLEY.get())
                .unlockedBy("has_barley", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.BARLEY.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.LEMON_SAPLING.get())
                .requires(ModItems.LEMON.get())
                .requires(Blocks.OAK_SAPLING)
                .unlockedBy("has_lemon", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.LEMON.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModBlocks.HAWTHORN_SAPLING.get())
                .requires(ModItems.HAWTHORN_BERRIES.get())
                .requires(Blocks.OAK_SAPLING)
                .unlockedBy("has_hawthorn_berries", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.HAWTHORN_BERRIES.get()).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MOLASSES.get())
                .requires(Items.GLASS_BOTTLE)
                .requires(Items.SUGAR_CANE)
                .requires(Items.SUGAR_CANE)
                .requires(Items.SUGAR_CANE)
                .unlockedBy("has_sugar_cane", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.SUGAR_CANE).build()))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.HOMESTEADERS_HANDBOOK.get())
                .requires(Items.BOOK)
                .requires(Items.WHEAT)
                .unlockedBy("has_book",
                        inventoryTrigger(ItemPredicate.Builder.item().of(Items.BOOK).build()))
                .save(recipeOutput);

        // Convertible for Storage Bags
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.ELDERBERRIES.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_ELDERBERRIES.get(),
                "yeastnfeast:elderberries", "elderberries","yeastnfeast:bag_of_elderberries", "elderberries");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.GARLIC.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_GARLIC.get(),
                "yeastnfeast:garlic", "garlic","yeastnfeast:bag_of_garlic", "garlic");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.GINGER.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_GINGER.get(),
                "yeastnfeast:ginger", "ginger","yeastnfeast:bag_of_ginger", "ginger");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.HAWTHORN_BERRIES.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_HAWTHORN_BERRIES.get(),
                "yeastnfeast:hawthorn_berries", "hawthorn_berries","yeastnfeast:bag_of_hawthorn_berries", "hawthorn_berries");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.LEMON.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_LEMON.get(),
                "yeastnfeast:lemon", "lemon","yeastnfeast:bag_of_lemon", "lemon");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.MINT.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_MINT.get(),
                "yeastnfeast:mint", "mint","yeastnfeast:bag_of_mint", "mint");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.ROSE_HIPS.get(), RecipeCategory.MISC, ModBlocks.BAG_OF_ROSE_HIPS.get(),
                "yeastnfeast:rose_hips", "rose_hips","yeastnfeast:bag_of_rose_hips", "rose_hips");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.BARLEY.get(), RecipeCategory.MISC, ModBlocks.BARLEY_BLOCK.get(),
                "yeastnfeast:barley", "barley","yeastnfeast:barley_block", "barley");
        nineBlockStorageRecipes(recipeOutput, RecipeCategory.MISC, ModItems.RYE.get(), RecipeCategory.MISC, ModBlocks.RYE_BLOCK.get(),
                "yeastnfeast:rye", "rye","yeastnfeast:rye_block", "rye");

        // Recipes for Wood-related Blocks & Items
        planksFromLog(recipeOutput, ModBlocks.MAPLE_PLANKS.get(), ModTags.Items.MAPLE_LOGS, 4);
        oneToOneConversionRecipe(recipeOutput, ModBlocks.MAPLE_BUTTON.get(), ModBlocks.MAPLE_PLANKS.get(), "wooden_button");
        trapdoorBuilder(ModBlocks.MAPLE_TRAPDOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        doorBuilder(ModBlocks.MAPLE_DOOR.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        pressurePlateBuilder(RecipeCategory.REDSTONE, ModBlocks.MAPLE_PRESSURE_PLATE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        stairBuilder(ModBlocks.MAPLE_STAIRS.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        slabBuilder(RecipeCategory.DECORATIONS, ModBlocks.MAPLE_SLAB.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        fenceBuilder(ModBlocks.MAPLE_FENCE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        fenceGateBuilder(ModBlocks.MAPLE_FENCE_GATE.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        signBuilder(ModBlocks.MAPLE_SIGN.get(), Ingredient.of(ModBlocks.MAPLE_PLANKS.get()))
                .unlockedBy("has_planks", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MAPLE_PLANKS.get()).build()))
                .save(recipeOutput);
        hangingSign(recipeOutput, ModItems.MAPLE_HANGING_SIGN.get(), ModBlocks.STRIPPED_MAPLE_LOG.get());
        woodFromLogs(recipeOutput, ModBlocks.MAPLE_WOOD.get(), ModBlocks.MAPLE_LOG.get());

        chestBoat(recipeOutput, ModItems.MAPLE_CHEST_BOAT.get(), ModItems.MAPLE_BOAT.get());
        woodenBoat(recipeOutput, ModItems.MAPLE_BOAT.get(), ModBlocks.MAPLE_PLANKS.get());
    }
}
