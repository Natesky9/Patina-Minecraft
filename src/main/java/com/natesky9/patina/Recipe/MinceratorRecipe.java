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

public record MinceratorRecipe(ItemStack input1,ItemStack input2,ItemStack input3, ItemStack output) implements Recipe<MinceratorRecipeInput> {
    @Override
    public boolean matches(MinceratorRecipeInput recipeInput, Level level) {
        ItemStack stack1 = recipeInput.getItem(0);
        ItemStack stack2 = recipeInput.getItem(1);
        ItemStack stack3 = recipeInput.getItem(2);

        boolean match1 = stack1.is(input1.getItem()) && stack2.is(input2.getItem()) && stack3.is(input3.getItem());
        boolean match2 = stack1.is(input2.getItem()) && stack2.is(input3.getItem()) && stack3.is(input1.getItem());
        boolean match3 = stack1.is(input3.getItem()) && stack2.is(input1.getItem()) && stack3.is(input2.getItem());

        return match1 || match2 || match3;
    }

    @Override
    public ItemStack assemble(MinceratorRecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<MinceratorRecipeInput>> getSerializer() {
        return ModRecipeSerializers.MINCERATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<MinceratorRecipeInput>> getType() {
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
                        ItemStack.CODEC.fieldOf("input1").forGetter(MinceratorRecipe::input1),
                        ItemStack.CODEC.fieldOf("input2").forGetter(MinceratorRecipe::input2),
                        ItemStack.CODEC.fieldOf("input3").forGetter(MinceratorRecipe::input3),
                        ItemStack.CODEC.fieldOf("output").forGetter(MinceratorRecipe::output)
                ).apply(builder, MinceratorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MinceratorRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC,MinceratorRecipe::input1,
                        ItemStack.STREAM_CODEC,MinceratorRecipe::input2,
                        ItemStack.STREAM_CODEC,MinceratorRecipe::input3,
                        ItemStack.STREAM_CODEC, MinceratorRecipe::output,
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
