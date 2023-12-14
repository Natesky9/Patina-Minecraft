package com.natesky9.patina.Recipe;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.natesky9.patina.init.ModRecipeSerializers;
import com.natesky9.patina.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.tags.TagKey;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class MinceratorRecipe implements Recipe<SimpleContainer> {
    public static final String name = "smoker_grindstone";
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    public static int match;

    public MinceratorRecipe(ItemStack output,
                            NonNullList<Ingredient> recipeItems)
    {
        this.output = output;
        this.recipeItems = recipeItems;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        match = 0;
        ArrayList<ItemStack> suppliedItems = new ArrayList<>();
        for (int i=3; i >= 0;i--)
        {
            ItemStack stack = pContainer.getItem(i);
            if (stack.isEmpty()) return false;
            suppliedItems.add(stack);
        }
        if (suppliedItems.size() < recipeItems.size()) return false;
        boolean matches = true;//have to initialize or else it yells at me
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
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess access) {
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return output.copy();
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.MINCERATOR_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType()
    {
        return ModRecipeTypes.MINCERATOR_RECIPE_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<MinceratorRecipe> {
        public static final Codec<Either<Ingredient,TagKey<Item>>> ITEM_OR_TAG_CODEC =
                Codec.either(Ingredient.CODEC, TagKey.codec(BuiltInRegistries.ITEM.key()));

        Codec<MinceratorRecipe> CODEC = RecordCodecBuilder.create((instance) ->
                instance.group(
                        CraftingRecipeCodecs.ITEMSTACK_OBJECT_CODEC.fieldOf("output").forGetter((getter) -> getter.output),
                        Ingredient.CODEC.listOf().fieldOf("ingredients").flatXmap(
                                (map) ->
                                {
                                    NonNullList<Ingredient> list = NonNullList.create();
                                    list.addAll(map);
                                    if (list.size() != 4)
                                    {
                                        return DataResult.error(() -> "recipe does not adhere to 4 items!");
                                    }
                                    return DataResult.success(list);
                                }, DataResult::success).forGetter((getter) -> getter.recipeItems
                        )
                ).apply(instance, MinceratorRecipe::new));
        @Override
        public Codec<MinceratorRecipe> codec() {
            return CODEC;
        }
        //@Override
        //public MinceratorRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
        //    @Nullable ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        //    ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
        //    JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
        //    ArrayList<Object> inputs = new ArrayList<>();
//
        //    for (int i = 0; i < ingredients.size(); i++) {
        //        JsonObject test = ((JsonObject) ingredients.get(i));
        //        if (test.has("item"))
        //            inputs.add(i, Ingredient.fromJson(ingredients.get(i)));
        //        if (test.has("tag"))
        //        {
        //            String str = test.get("tag").getAsString();
        //            ResourceLocation parse = ResourceLocation.tryParse(str);
        //            TagKey<Item> tag = tagManager.createTagKey(parse);
        //            int count = test.has("count") ? test.get("count").getAsInt():1;
        //            for (int c=0;c < count;c++)
        //                inputs.add(inputs.size(), tag);
        //        }
        //    }
        //    return new MinceratorRecipe(output, inputs);
        //}


        @Nullable
        @Override
        public MinceratorRecipe fromNetwork(FriendlyByteBuf buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(4,Ingredient.EMPTY);

            int size = buffer.readInt();
            for (int i = 0; i < size; i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new MinceratorRecipe(output, inputs);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MinceratorRecipe pRecipe) {
            buffer.writeInt(pRecipe.getIngredients().size());
            for (Ingredient object : pRecipe.getIngredients())
            {
                object.toNetwork(buffer);
            }
            buffer.writeItemStack(pRecipe.output, false);
        }
    }
}
