package com.natesky9.patina.Recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record MatrixRecipe(ItemStack input, ItemStack output) implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return null;
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipeSerializers.MATRIX_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.MATRIX_RECIPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
    //serializer stuff
    public static class Serializer implements RecipeSerializer<MatrixRecipe>
    {
        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<MatrixRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        ItemStack.CODEC.fieldOf("input").forGetter(MatrixRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(MatrixRecipe::output)
                ).apply(builder, MatrixRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MatrixRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC,MatrixRecipe::input,ItemStack.STREAM_CODEC, MatrixRecipe::output,
                        MatrixRecipe::new);
        @Override
        public MapCodec<MatrixRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MatrixRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
