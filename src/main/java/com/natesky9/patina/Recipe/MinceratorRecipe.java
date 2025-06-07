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

public record MinceratorRecipe(ItemStack input, ItemStack output) implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        return input.is(recipeInput.getItem(0).getItem());
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<RecipeInput>> getSerializer() {
        return ModRecipeSerializers.MINCERATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.MINCERATOR_RECIPE.get();
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
    public static class Serializer implements RecipeSerializer<MinceratorRecipe>
    {
        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<MinceratorRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        ItemStack.CODEC.fieldOf("input").forGetter(MinceratorRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(MinceratorRecipe::output)
                ).apply(builder, MinceratorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MinceratorRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC,MinceratorRecipe::input,ItemStack.STREAM_CODEC, MinceratorRecipe::output,
                        MinceratorRecipe::new);

        @Override
        public MapCodec<MinceratorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MinceratorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
