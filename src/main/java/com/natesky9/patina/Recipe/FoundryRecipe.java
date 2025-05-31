package com.natesky9.patina.Recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;


public record FoundryRecipe(Holder<Item> input, Holder<Item> output, int count) implements Recipe<RecipeInput> {
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
        return ModRecipeSerializers.FOUNDRY_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<RecipeInput>> getType() {
        return ModRecipeTypes.FOUNDRY_RECIPE.get();
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
    public static class Serializer implements RecipeSerializer<FoundryRecipe>
    {
        //TODO:replace itemstack/itemstack with better recipe arguments
        public static final MapCodec<FoundryRecipe> CODEC = RecordCodecBuilder.mapCodec(
                builder -> builder.group(
                        Item.CODEC.fieldOf("input").forGetter(FoundryRecipe::input),
                        Item.CODEC.fieldOf("output").forGetter(FoundryRecipe::output),
                        Codec.INT.fieldOf("count").forGetter(FoundryRecipe::count)
                ).apply(builder, FoundryRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FoundryRecipe> STREAM_CODEC =
                StreamCodec.composite(ByteBufCodecs.holderRegistry(Registries.ITEM),FoundryRecipe::input,
                        ByteBufCodecs.holderRegistry(Registries.ITEM), FoundryRecipe::output,
                        ByteBufCodecs.INT, FoundryRecipe::count,
                        FoundryRecipe::new);
        @Override
        public MapCodec<FoundryRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FoundryRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
