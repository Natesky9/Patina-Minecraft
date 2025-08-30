package com.natesky9.patina.Recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record SieveRecipe(ItemStack input, ItemStack output, boolean water) implements Recipe<SieveRecipeInput> {
    @Override
    public boolean matches(SieveRecipeInput recipeInput, Level level) {
        boolean same = water ^ recipeInput.water();
        boolean match = input.is(recipeInput.getItem(0).getItem());
        return same && match;
    }

    @Override
    public ItemStack assemble(SieveRecipeInput recipeInput, HolderLookup.Provider provider) {
        return output.copy();
    }

    @Override
    public RecipeSerializer<? extends Recipe<SieveRecipeInput>> getSerializer() {
        return ModRecipeSerializers.SIEVE_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<SieveRecipeInput>> getType() {
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
                        ItemStack.CODEC.fieldOf("item1").forGetter(SieveRecipe::input),
                        ItemStack.CODEC.fieldOf("item3").forGetter(SieveRecipe::output),
                        Codec.BOOL.fieldOf("water").forGetter(SieveRecipe::water)
                ).apply(builder, SieveRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SieveRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC, SieveRecipe::input,
                        ItemStack.STREAM_CODEC, SieveRecipe::output,
                        ByteBufCodecs.BOOL,SieveRecipe::water,
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
