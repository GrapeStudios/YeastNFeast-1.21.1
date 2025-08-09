package net.grapes.yeastnfeast.recipe;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, YeastNFeastMod.MODID);

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, YeastNFeastMod.MODID);

    public static final Supplier<RecipeType<KegRecipe>> KEG_TYPE =
            RECIPE_TYPES.register("keg", () -> new RecipeType<KegRecipe>() {
                @Override
                public String toString() {
                    return "keg";
                }
            });

    public static final Supplier<RecipeSerializer<KegRecipe>> KEG_SERIALIZER =
            RECIPE_SERIALIZERS.register("keg", KegRecipe.Serializer::new);

    public static void register(IEventBus eventBus) {
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
