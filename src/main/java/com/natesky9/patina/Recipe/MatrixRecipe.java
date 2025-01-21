package com.natesky9.patina.Recipe;

import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class MatrixRecipe implements Recipe<RecipeInput> {
    public static final String name = "matrix";
    private final ItemStack output;
    private final List<Ingredient> recipeItems;

    public MatrixRecipe(ItemStack output,
                        List<Ingredient> recipeItems)
    {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> copy = NonNullList.createWithCapacity(recipeItems.size());
        copy.addAll(recipeItems);
        return copy;
    }

    @Override
    public boolean matches(RecipeInput pContainer, Level pLevel) {
        if (pContainer.size() != recipeItems.size()) return false;

        //copied from mincerator
        //I had to change size to be 1 less to work, check mincerator
        ArrayList<ItemStack> suppliedItems = new ArrayList<>();
        for (int i=recipeItems.size()-1; i >= 0;i--)
        {
            ItemStack stack = pContainer.getItem(i);
            if (stack.isEmpty()) return false;
            suppliedItems.add(stack);
        }
        if (suppliedItems.size() < recipeItems.size()) return false;
        boolean matches;//have to initialize or else it yells at me
        ItemStack holder;
        for (int i = 0;i < recipeItems.size();i++)
        {
            if (recipeItems.get(i).test(suppliedItems.get(i))) continue;
            matches = false;
            for (int r = i; r < suppliedItems.size();r++)
            {
                if (recipeItems.get(i).test(suppliedItems.get(r)))
                {
                    holder = suppliedItems.get(i);
                    suppliedItems.set(i,suppliedItems.get(r));
                    suppliedItems.set(r,holder);
                    matches = true;
                    break;
                }
            }
            //is this line needed anymore?
            if (!matches) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(RecipeInput pContainer, HolderLookup.Provider access) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider access) {
        return output.copy();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MATRIX_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.MATRIX_RECIPE_TYPE.get();
    }
    //TODO: Serializer
    public static class Serializer implements RecipeSerializer<MatrixRecipe> {


        public static final MapCodec<MatrixRecipe> CODEC = RecordCodecBuilder.mapCodec((instance) ->
                instance.group(
                        ItemStack.CODEC.fieldOf("output").forGetter((getter) -> getter.output),
                        Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(
                                (map) ->
                                {
                                    List<Ingredient> list = NonNullList.create();
                                    list.addAll(map);
                                    return DataResult.success(list);
                                }, DataResult::success).forGetter((getter) -> getter.recipeItems
                        )
                ).apply(instance, MatrixRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, MatrixRecipe> STREAM_CODEC =
                StreamCodec.of(Serializer::encode, Serializer::decode
        );
        @Override
        public MapCodec<MatrixRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, MatrixRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void encode(RegistryFriendlyByteBuf buffer, MatrixRecipe pRecipe) {
            buffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient object : pRecipe.getIngredients())
            {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer,object);
                //object.toNetwork(buffer);
            }
            ItemStack.STREAM_CODEC.encode(buffer,pRecipe.output);
            //buffer.writeItemStack(pRecipe.output, false);
        }

        private static MatrixRecipe decode(RegistryFriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(9,Ingredient.EMPTY);

            int size = buffer.readInt();
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));
            }

            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            return new MatrixRecipe(output, inputs);
        }

    }
}
