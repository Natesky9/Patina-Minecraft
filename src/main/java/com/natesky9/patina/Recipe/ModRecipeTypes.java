package com.natesky9.patina.Recipe;

import com.natesky9.patina.Patina;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, Patina.MODID);
    //
    public static final DeferredHolder<RecipeType<?>,RecipeType<EvaporatorRecipe>> EVAPORATOR_RECIPE = create("evaporator");
    public static final DeferredHolder<RecipeType<?>,RecipeType<FoundryRecipe>> FOUNDRY_RECIPE = create("foundry");
    public static final DeferredHolder<RecipeType<?>,RecipeType<MinceratorRecipe>> MINCERATOR_RECIPE = create("mincerator");
    public static final DeferredHolder<RecipeType<?>,RecipeType<TextilerRecipe>> TEXTILER_RECIPE = create("textiler");
    public static final DeferredHolder<RecipeType<?>,RecipeType<MinceratorRecipe>> KWERN_RECIPE = create("kwern");
    public static final DeferredHolder<RecipeType<?>,RecipeType<MinceratorRecipe>> MATRIX_RECIPE = create("matrix");
    //
    static <T extends Recipe<?>> DeferredHolder<RecipeType<?>,RecipeType<T>> create(String id)
    {
        return RECIPE_TYPES.register(id, () -> new RecipeType<T>() {
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
