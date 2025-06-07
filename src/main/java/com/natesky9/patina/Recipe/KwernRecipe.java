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
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record KwernRecipe(ItemStack input, ItemStack output, int every) implements Recipe<RecipeInput> {
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
        return ModRecipeSerializers.KWERN_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.KWERN_RECIPE.get();
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
    public static class Serializer implements RecipeSerializer<KwernRecipe>
    {
        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<KwernRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        ItemStack.CODEC.fieldOf("input").forGetter(KwernRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(KwernRecipe::output),
                        ExtraCodecs.POSITIVE_INT.fieldOf("every").forGetter(KwernRecipe::every)
                ).apply(builder, KwernRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, KwernRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        ItemStack.STREAM_CODEC,KwernRecipe::input,
                        ItemStack.STREAM_CODEC, KwernRecipe::output,
                        ByteBufCodecs.INT, KwernRecipe::every,
                        KwernRecipe::new);
        @Override
        public MapCodec<KwernRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, KwernRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
