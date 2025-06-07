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

public record TextilerRecipe(ItemStack input, ItemStack output) implements Recipe<RecipeInput> {
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
        return ModRecipeSerializers.TEXTILER_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.TEXTILER_RECIPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
    public static class Serializer implements RecipeSerializer<TextilerRecipe>
    {

        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<TextilerRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        ItemStack.CODEC.fieldOf("input").forGetter(TextilerRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(TextilerRecipe::output)
                ).apply(builder, TextilerRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, TextilerRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC,TextilerRecipe::input,ItemStack.STREAM_CODEC, TextilerRecipe::output,
                        TextilerRecipe::new);

        @Override
        public MapCodec<TextilerRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, TextilerRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
