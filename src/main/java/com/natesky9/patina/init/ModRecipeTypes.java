package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Patina.MODID);
    //
    public static final DeferredHolder<RecipeType<?>,RecipeType<EvaporatorRecipe>> EVAPORATOR_RECIPE = create("evaporator");
    public static final DeferredHolder<RecipeType<?>,RecipeType<FoundryRecipe>> FOUNDRY_RECIPE = create("foundry");
    public static final DeferredHolder<RecipeType<?>,RecipeType<MinceratorRecipe>> MINCERATOR_RECIPE = create("mincerator");
    public static final DeferredHolder<RecipeType<?>,RecipeType<TextilerRecipe>> TEXTILER_RECIPE = create("textiler");
    public static final DeferredHolder<RecipeType<?>,RecipeType<KwernRecipe>> KWERN_RECIPE = create("kwern");
    public static final DeferredHolder<RecipeType<?>,RecipeType<SieveRecipe>> SIEVE_RECIPE = create("sieve");
    public static final DeferredHolder<RecipeType<?>,RecipeType<MatrixRecipe>> MATRIX_RECIPE = create("matrix");
    //
    static <T extends Recipe<?>> DeferredHolder<RecipeType<?>,RecipeType<T>> create(String id)
    {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return id;
            }
        });
    }
    public static void register(IEventBus eventBus)
    {
        RECIPE_TYPES.register(eventBus);
    }
}
