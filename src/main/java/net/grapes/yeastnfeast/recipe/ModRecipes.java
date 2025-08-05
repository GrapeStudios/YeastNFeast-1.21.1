package net.grapes.yeastnfeast.recipe;

import net.grapes.yeastnfeast.YeastNFeastMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, YeastNFeastMod.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(Registries.RECIPE_TYPE, YeastNFeastMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<KegRecipe>> KEG_SERIALIZER =
            SERIALIZERS.register("keg", KegRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<KegRecipe>> KEG_TYPE =
            TYPES.register("keg", () -> new RecipeType<KegRecipe>() {
                @Override
                public String toString() {
                    return "keg";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
