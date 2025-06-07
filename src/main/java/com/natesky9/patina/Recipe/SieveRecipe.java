package com.natesky9.patina.Recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record SieveRecipe(ItemStack input, ItemStack output, ItemStack secondary, int every) implements Recipe<RecipeInput> {
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
        return ModRecipeSerializers.SIEVE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.SIEVE_RECIPE.get();
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
    public static class Serializer implements RecipeSerializer<SieveRecipe>
    {
        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<SieveRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        ItemStack.CODEC.fieldOf("input").forGetter(SieveRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(SieveRecipe::output),
                        ItemStack.CODEC.fieldOf("secondary").forGetter(SieveRecipe::secondary),
                        ExtraCodecs.POSITIVE_INT.fieldOf("every").forGetter(SieveRecipe::every)
                ).apply(builder, SieveRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SieveRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC, SieveRecipe::input,
                        ItemStack.STREAM_CODEC, SieveRecipe::output,
                        ItemStack.STREAM_CODEC, SieveRecipe::secondary,
                        ByteBufCodecs.INT, SieveRecipe::every,
                        SieveRecipe::new);
        @Override
        public MapCodec<SieveRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SieveRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
