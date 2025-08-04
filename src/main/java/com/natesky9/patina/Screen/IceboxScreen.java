package com.natesky9.patina.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.natesky9.patina.Menu.IceboxMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IceboxScreen extends AbstractContainerScreen<IceboxMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/icebox.png");
    private final ResourceLocation FOOD = ResourceLocation.parse("hud/food_full");
    private final ResourceLocation FOOD_HALF = ResourceLocation.parse("hud/food_half");
    IceboxMenu icebox;
    public IceboxScreen(IceboxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        icebox = menu;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, leftPos, topPos,
                0, 0, this.imageWidth, this.imageHeight, 256, 256);

        if (hoveredSlot != null && hoveredSlot.hasItem())
        {
            int x = (width-imageWidth)/2+8;
            int y = (height-imageHeight)/2+8;
            guiGraphics.drawString(font,hoveredSlot.getItem().getItem().getName(hoveredSlot.getItem()),x,y-4, -1);
            int yoffset = 0;
            ItemStack stack = hoveredSlot.getItem();
            MobEffectTextureManager mobEffectTextureManager = minecraft.getMobEffectTextures();
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partial) {
        super.render(graphics, mouseX, mouseY, partial);

        if (hoveredSlot != null && hoveredSlot.hasItem())
        {
            int x = (width-imageWidth)/2+8;
            int y = (height-imageHeight)/2+8;
            graphics.drawString(font,hoveredSlot.getItem().getItem().getName(hoveredSlot.getItem()),x,y-4, -1);
            int yoffset = 0;
            ItemStack stack = hoveredSlot.getItem();
            MobEffectTextureManager mobEffectTextureManager = minecraft.getMobEffectTextures();

            FoodProperties foodProperties = stack.get(DataComponents.FOOD);
            if (foodProperties != null)
            {//draw hunger
                RenderSystem.setShaderTexture(0,FOOD);
                y += 8;
                //draw the hunger haunches
                int hunger = foodProperties.nutrition();
                for (int i=0; i< hunger; i+=2)
                {
                    if (i != hunger - 1)
                        graphics.blitSprite(RenderType::guiTextured,FOOD,x+i*4,y+yoffset,9,9);
                    else
                        graphics.blitSprite(RenderType::guiTextured,FOOD_HALF,x+i*4,y+yoffset,9,9);
                }
                yoffset += 8;
            }
            Consumable consumable = stack.get(DataComponents.CONSUMABLE);
            if (consumable != null)
            {
                List<ConsumeEffect> effects = consumable.onConsumeEffects();

                for (int i=0; i<effects.size(); i++)
                {
                    ConsumeEffect consumeEffect = effects.get(i);
                    if (consumeEffect.getType() == ConsumeEffect.Type.APPLY_EFFECTS)
                    {
                        List<MobEffectInstance> status = ((ApplyStatusEffectsConsumeEffect)consumeEffect).effects();
                        int probability = (int) (((ApplyStatusEffectsConsumeEffect)consumeEffect).probability()*100);
                        for (int s=0; s<status.size(); s++)
                        {
                            MobEffectInstance instance = status.get(s);
                            TextureAtlasSprite sprite = mobEffectTextureManager.get(instance.getEffect());
                            RenderSystem.setShaderTexture(0,sprite.atlasLocation());
                            graphics.blitSprite(RenderType::guiTextured,sprite,x,y+yoffset+s*16,16,16);
                            if (probability < 100)
                            {
                                graphics.drawString(font,probability + "%",x+16+4,y+yoffset+s*16+4,-1);
                            }
                            if (!instance.getEffect().value().isInstantenous())
                            {
                                String duration = StringUtil.formatTickDuration(instance.getDuration(),minecraft.level.tickRateManager().tickrate());
                                int offset = probability < 100 ? 20:0;
                                graphics.drawString(font,duration,x+20+offset,y+yoffset+s*16+4,16777215);
                            }
                        }
                    }
                }
                yoffset += 8;
            }
            //end consumable
            PotionContents potion = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
            if (potion != PotionContents.EMPTY)
            {
                int i=0;
                for (MobEffectInstance instance:potion.getAllEffects())
                {
                    //MobEffect effect = instance.getEffect().value();
                    int potency = instance.getAmplifier();
                    TextureAtlasSprite sprite = mobEffectTextureManager.get(instance.getEffect());
                    RenderSystem.setShaderTexture(0,sprite.atlasLocation());
                    for (int p=0;p<=potency;p++)
                    {
                        graphics.blitSprite(RenderType::guiTextured,sprite,x+p*4,y+yoffset+i*16,16,16);
                    }

                    if (!instance.getEffect().value().isInstantenous())
                    {
                        String duration = StringUtil.formatTickDuration(instance.getDuration(),minecraft.level.tickRateManager().tickrate());
                        graphics.drawString(font,duration,x+20+potency*4,y+yoffset+i*16+4,16777215);
                    }
                    i++;
                }
            }
        }
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
        //if (slot.index>=0 && slot.index < 20)
        //{
        //    ItemStack stack = slot.getItem();
        //    if (stack.getPopTime() > 0)
        //        stack.setPopTime(stack.getPopTime()-1);
        //}
    }

    @Override
    protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemstack, Slot slot, @Nullable String countString) {

        int i = slot.x;
        int j = slot.y;
        int j1 = slot.x + slot.y * this.imageWidth;

        ItemStack stack = slot.getItem();
        float f = (float)stack.getPopTime() - minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(i + 8), (float)(j + 12), 0.0F);
            guiGraphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            guiGraphics.pose().translate((float)(-(i + 8)), (float)(-(j + 12)), 0.0F);
        }

        guiGraphics.renderItem(itemstack, i, j, j1);

        guiGraphics.renderItemDecorations(this.font, itemstack, i, j, countString);
    }
}
