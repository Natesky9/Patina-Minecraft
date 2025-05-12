package com.natesky9.patina.Recipe;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, Patina.MODID);
    //
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<EvaporatorRecipe>> EVAPORATOR_SERIALIZER =
            RECIPE_SERIALIZERS.register("evaporator", EvaporatorRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<FoundryRecipe>> FOUNDRY_SERIALIZER =
            RECIPE_SERIALIZERS.register("foundry", FoundryRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<KwernRecipe>> KWERN_SERIALIZER =
            RECIPE_SERIALIZERS.register("kwern", KwernRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<MatrixRecipe>> MATRIX_SERIALIZER =
            RECIPE_SERIALIZERS.register("matrix", MatrixRecipe.Serializer::new);
    public static final DeferredHolder<RecipeSerializer<?>,RecipeSerializer<MinceratorRecipe>> MINCERATOR_SERIALIZER =
            RECIPE_SERIALIZERS.register("mincerator", MinceratorRecipe.Serializer::new);
    //
    public static void register(IEventBus eventBus)
    {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
