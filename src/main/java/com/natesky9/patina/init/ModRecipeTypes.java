package com.natesky9.patina.init;

import com.natesky9.patina.Recipe.*;
import com.natesky9.patina.Patina;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModRecipeTypes
{
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Patina.MODID);
    //
    public static final RegistryObject<RecipeType<MatrixRecipe>> MATRIX_RECIPE_TYPE = create(MatrixRecipe.name);
    public static final RegistryObject<RecipeType<EvaporatorRecipe>> EVAPORATOR_RECIPE_TYPE = create(EvaporatorRecipe.name);
    public static final RegistryObject<RecipeType<FoundryRecipe>> FOUNDRY_RECIPE_TYPE = create(FoundryRecipe.name);
    public static final RegistryObject<RecipeType<MinceratorRecipe>> MINCERATOR_RECIPE_TYPE = create(MinceratorRecipe.name);
    public static final RegistryObject<RecipeType<TextilerRecipe>> TEXTILER_RECIPE_TYPE = create(TextilerRecipe.name);
    //
    static <T extends Recipe<?>> RegistryObject<RecipeType<T>> create(final String id)
    {
        return RECIPE_TYPES.register(id, () -> new RecipeType<T>()
        {
            @Override//goofy ahh spacing
            public String toString() {return id;}});
    }
    //
    public static void register(IEventBus eventBus)
    {
        RECIPE_TYPES.register(eventBus);
    }

}
