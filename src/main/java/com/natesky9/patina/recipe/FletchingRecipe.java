package com.natesky9.patina.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.natesky9.patina.init.ModItems;
import com.natesky9.patina.init.ModRecipeTypes;
import com.natesky9.patina.init.ModRecipeSerializers;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class FletchingRecipe implements Recipe<SimpleContainer> {
    //recipe type
    //that takes in a tool
    //and a resource, outputting a component
    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> inputs;

    public FletchingRecipe(ResourceLocation id, ItemStack output,
                           NonNullList<Ingredient> recipeItems)
    {
        this.id = id;
        this.output = output;
        this.inputs = recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        boolean component1 = inputs.get(0).test(pContainer.getItem(0));
        boolean component2 = inputs.get(1).test(pContainer.getItem(1));
        if (component1 && component1) assemble(pContainer);
        return component1 && component2;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        //gem
        ItemStack item1 = pContainer.getItem(0);
        //bolts
        ItemStack item2 = pContainer.getItem(1);

        //bolt tips + bolts
        if (item1.is(ModItems.BOLT_TIPS.get()) && item2.is(ModItems.BOLTS.get()))
        {
        output.getOrCreateTag().putString("gem",item1.getOrCreateTag().getString("gem"));
        output.getOrCreateTag().putInt("gem color", item1.getOrCreateTag().getInt("color"));
        output.getOrCreateTag().putString("metal",item2.getOrCreateTag().getString("metal"));
        output.getOrCreateTag().putInt("metal color", item2.getOrCreateTag().getInt("metal color"));
        }
        if (item1.is(ModItems.UNFINISHED_BOLTS.get()) && item2.is(Tags.Items.FEATHERS))
        {
            output.getOrCreateTag().putString("metal", item1.getOrCreateTag().getString("metal"));
            output.getOrCreateTag().putInt("metal color", item1.getOrCreateTag().getInt("color"));

            String name = item2.getItem().getCreatorModId(item2) + ":" + item2.getItem();
            output.getOrCreateTag().putString("feather",name);
        }
            System.out.println(output);
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return output.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipeTypes.FLETCHING_RECIPE_TYPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<FletchingRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public FletchingRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));
            JsonArray ingredients = GsonHelper.getAsJsonArray(json,"inputs");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2,Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++)
            {inputs.set(i,Ingredient.fromJson(ingredients.get(i)));}

            return new FletchingRecipe(pRecipeId, output, inputs);
        }

        @Nullable
        @Override
        public FletchingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(),Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++)
            {inputs.set(i, Ingredient.fromNetwork(buffer));}

            ItemStack output = buffer.readItem();
            return new FletchingRecipe(id, output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, FletchingRecipe pRecipe) {

            buffer.writeInt(pRecipe.getIngredients().size());

            for (Ingredient ingredient: pRecipe.getIngredients())
            {ingredient.toNetwork(buffer);}

            buffer.writeItemStack(pRecipe.getResultItem(),false);
        }
    }
}
