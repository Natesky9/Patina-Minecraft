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

import static net.minecraft.world.item.ItemStack.STREAM_CODEC;


public record FoundryRecipe(ItemStack item1, ItemStack item2, ItemStack item3, boolean alloy) implements Recipe<RecipeInput> {
    @Override
    public boolean matches(RecipeInput recipeInput, Level level) {
        if (!alloy && recipeInput instanceof AlloyRecipeInput)
            return item1.is(recipeInput.getItem(0).getItem());
        if (alloy && recipeInput instanceof AlloyRecipeInput)
            return item1.is(recipeInput.getItem(0).getItem()) &&
                    item2.is(recipeInput.getItem(1).getItem());
        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput recipeInput, HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
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
                        net.minecraft.world.item.ItemStack.CODEC.fieldOf("item1").forGetter(FoundryRecipe::item1),
                        net.minecraft.world.item.ItemStack.CODEC.fieldOf("item2").forGetter(FoundryRecipe::item2),
                        net.minecraft.world.item.ItemStack.CODEC.fieldOf("item3").forGetter(FoundryRecipe::item3),
                        Codec.BOOL.fieldOf("alloy").forGetter(FoundryRecipe::alloy)
                ).apply(builder, FoundryRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, FoundryRecipe> STREAM_CODEC =
                StreamCodec.composite(ItemStack.STREAM_CODEC,FoundryRecipe::item1,
                        ItemStack.STREAM_CODEC, FoundryRecipe::item2,
                        ItemStack.STREAM_CODEC, FoundryRecipe::item3,
                        ByteBufCodecs.BOOL, FoundryRecipe::alloy,
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
