package com.natesky9.patina.recipe;

import com.google.gson.JsonObject;
import com.natesky9.patina.init.ModRecipeTypes;
import com.natesky9.patina.item.HammerItem;
import com.natesky9.patina.item.KnifeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class KnifeRecipe implements Recipe<SimpleContainer> {
    //recipe type
    //that takes in a tool
    //and a resource, outputting a component
    private final ResourceLocation id;
    public static final String name = "knife";
    private final ItemStack output;
    private final Ingredient input;
    private final int color;

    public KnifeRecipe(ResourceLocation id, ItemStack output,
                       Ingredient input, int color)
    {
        this.id = id;
        this.output = output;
        this.input = input;
        this.color = color;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        boolean tool = pContainer.getItem(0).getItem() instanceof KnifeItem;
        boolean item = input.test(pContainer.getItem(1));
        if (tool && item) assemble(pContainer);
        return tool && item;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer) {
        //hammer is always index 0
        ItemStack item = pContainer.getItem(1);
        String name = item.getItem().getCreatorModId(item) + ":" + item.getItem();
        System.out.println("item crafted: " + name);

        output.getOrCreateTag().putString("wood",name);
        output.getOrCreateTag().putInt("wood color",color);
        return output;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
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
        return ModRecipeTypes.KNIFE_RECIPE_TYPE.get();
    }

    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<KnifeRecipe>
    {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public KnifeRecipe fromJson(ResourceLocation pRecipeId, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json,"input"));
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json,"output"));
            int color = GsonHelper.getAsInt(json,"color");

            return new KnifeRecipe(pRecipeId, output, input, color);
        }

        @Nullable
        @Override
        public KnifeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            int color = buffer.readInt();
            return new KnifeRecipe(id, output, input, color);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, KnifeRecipe pRecipe) {
            //write the input, output, and color
            pRecipe.input.toNetwork(buffer);
            buffer.writeItemStack(pRecipe.getResultItem(),false);
            buffer.writeInt(pRecipe.color);
        }
    }
}
