package com.natesky9.patina.init;

import com.natesky9.patina.Patina;
import com.natesky9.patina.Recipe.*;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Patina.MODID);
    //
    public static final RegistryObject<RecipeSerializer<EvaporatorRecipe>> EVAPORATOR_SERIALIZER =
            SERIALIZERS.register(EvaporatorRecipe.name, EvaporatorRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<FoundryRecipe>> FOUNDRY_SERIALIZER =
            SERIALIZERS.register(FoundryRecipe.name, FoundryRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<MinceratorRecipe>> MINCERATOR_SERIALIZER =
            SERIALIZERS.register(MinceratorRecipe.name, MinceratorRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<TextilerRecipe>> TEXTILER_SERIALIZER =
            SERIALIZERS.register(TextilerRecipe.name, TextilerRecipe.Serializer::new);
    public static final RegistryObject<RecipeSerializer<MatrixRecipe>> MATRIX_SERIALIZER =
            SERIALIZERS.register(MatrixRecipe.name, MatrixRecipe.Serializer::new);
    //
    public static void register(IEventBus eventBus)
    {
        SERIALIZERS.register(eventBus);
    }
}
