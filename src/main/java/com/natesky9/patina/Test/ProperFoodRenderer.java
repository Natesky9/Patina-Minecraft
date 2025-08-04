package com.natesky9.patina.Test;

import com.natesky9.patina.init.ModAttributes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class ProperFoodRenderer {
    static final ResourceLocation FOOD_EMPTY_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty_hunger");
    static final ResourceLocation FOOD_HALF_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_half_hunger");
    static final ResourceLocation FOOD_FULL_HUNGER_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full_hunger");
    static final ResourceLocation FOOD_EMPTY_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_empty");
    static final ResourceLocation FOOD_HALF_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_half");
    static final ResourceLocation FOOD_FULL_SPRITE = ResourceLocation.withDefaultNamespace("hud/food_full");
    //BUG
    //when the player is at max (4 stacks) of gluttony, and eats an extra food,
    //it overflows the buffer used to send it or something? making the client think
    //it has the default 10 hunger, but does still correctly have the proper food value.
    //but who is going to stack 4x gluttony anyway?
    public static void renderHunger(GuiGraphics graphics)
    {
        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        AttributeInstance existing = player.getAttribute(ModAttributes.GLUTTONY_BLESSING);
        if (existing == null) return;

        FoodData fooddata = player.getFoodData();

        int max = (int)existing.getValue() * 20 / 2;
        //System.out.println("max is: " + max + "food is: " + fooddata.getFoodLevel());

        //int max = (int)existing.getValue()*20 / 2;
        Entity mount = player.getVehicle();
        if (mount == null){
            //probably change this to be depended on riding
            int rightHeight = 39;
            int x = graphics.guiWidth() / 2 + 91;
            int y = graphics.guiHeight() - rightHeight;
            //

            int food = fooddata.getFoodLevel();

            //
            for (int slot = 0; slot < max; slot++) {
                ResourceLocation empty;
                ResourceLocation half;
                ResourceLocation full;
                if (player.hasEffect(MobEffects.HUNGER)) {
                    empty = FOOD_EMPTY_HUNGER_SPRITE;
                    half = FOOD_HALF_HUNGER_SPRITE;
                    full = FOOD_FULL_HUNGER_SPRITE;
                } else {
                    empty = FOOD_EMPTY_SPRITE;
                    half = FOOD_HALF_SPRITE;
                    full = FOOD_FULL_SPRITE;
                }
                //controls the little food wiggle
                //if (player.getFoodData().getSaturationLevel() <= 0.0F && this.tickCount % (i * 3 + 1) == 0) {
                //    k = y + (this.random.nextInt(3) - 1);
                //}

                int horizontal = (x - (slot % 10) * 8 - 9);
                int vertical = y - (slot / 10)*8;
                graphics.blitSprite(RenderType::guiTextured, empty, horizontal, vertical, 9, 9);
                if (slot * 2 + 1 < food) {
                    graphics.blitSprite(RenderType::guiTextured, full, horizontal,vertical, 9, 9);
                }

                if (slot * 2 + 1 == food) {
                    graphics.blitSprite(RenderType::guiTextured, half, horizontal, vertical, 9, 9);
                }
            }

        }
    }
    public static boolean shouldRender()
    {
        if (Minecraft.getInstance().gameMode == null) return false;
        return Minecraft.getInstance().gameMode.canHurtPlayer();
    }
    public static void cancelVanilla(RenderGuiLayerEvent.Pre event)
    {

        Player player = Minecraft.getInstance().player;
        if (player == null) return;
        FoodData data = Minecraft.getInstance().player.foodData;
        if (!(data instanceof ProperFoodData)) return;

        if (!(event.getName() == VanillaGuiLayers.FOOD_LEVEL)) return;

        event.setCanceled(true);
    }
}
