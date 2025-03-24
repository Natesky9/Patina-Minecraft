package com.natesky9.patina.Recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

public record EvaporatorRecipe(Either<Ingredient, Holder<Potion>> input, ItemStack output) implements Recipe<RecipeInput> {
    //Thanks to Tslat for the help
    public static final String name = "evaporator";

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return getEither(this.input
                .mapBoth(ingredient -> NonNullList.of(Ingredient.EMPTY, ingredient),
                        potion -> NonNullList.of(Ingredient.EMPTY, Ingredient.of(Items.POTION))));
    }


    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        return getEither(
                this.input.mapBoth(ingredient -> {
                    boolean flag = ingredient.test(pContainer.getItem(0));
                    ItemStack potion = pContainer.getItem(0);
                    //this needs to be fixed
                    //boolean test = PotionContents.(potion).getEffects().stream().allMatch((effectInstance -> effectInstance.getAmplifier() == 0));
                    boolean effect = potion.get(DataComponents.POTION_CONTENTS).hasEffects();
                    if (flag && effect && true/*test*/) assemble(pContainer,pLevel.registryAccess());
                    return flag && effect;// && test;
                }, inputPotion -> {
                    Holder<Potion> potion = pContainer.getItem(0).get(DataComponents.POTION_CONTENTS).potion().get();//PotionUtils.getPotion(pContainer.getItem(0));
                    boolean flag = potion == inputPotion;
                    if (flag) assemble(pContainer,pLevel.registryAccess());
                    return flag;
                })
        );
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider provider) {
        //do stuff to the stack here
        this.input.ifLeft(ingredient -> output.set(DataComponents.POTION_CONTENTS,
                container.getItem(0).get(DataComponents.POTION_CONTENTS)));

        //if (input == null) do nothing as we're outputting a stack
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }


    @Override
    public ItemStack getResultItem(HolderLookup.Provider p_331967_) {
        return output.copy();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.EVAPORATOR_RECIPE_TYPE.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.create(this.getIngredients());
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return null;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.EVAPORATOR_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<EvaporatorRecipe> {
        public static final MapCodec<EvaporatorRecipe> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
                        Codec.either(Ingredient.CODEC_NONEMPTY, Potion.CODEC).fieldOf("input").forGetter(EvaporatorRecipe::input),
                        ItemStack.CODEC.fieldOf("output").forGetter(EvaporatorRecipe::output))
                .apply(builder, EvaporatorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, EvaporatorRecipe> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.either(Ingredient.CONTENTS_STREAM_CODEC, Potion.STREAM_CODEC), EvaporatorRecipe::input,
                ItemStack.STREAM_CODEC, EvaporatorRecipe::output,
                EvaporatorRecipe::new);

        @Override
        public MapCodec<EvaporatorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EvaporatorRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
    private static <T> T getEither(Either<T, T> either) {
        return either.left().orElseGet(() -> either.right().orElseThrow());
    }
}