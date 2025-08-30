package com.natesky9.patina.Screen.Research;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.*;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ResearchMinigamePairButton extends AbstractButton {
    ResearchMinigame screen;
    int value;
    boolean seen;
    Animal entity;
    public ResearchMinigamePairButton(int p_93365_, int p_93366_,ResearchMinigame screen, int value) {
        super(p_93365_, p_93366_, 32,32, Component.empty());
        if (screen.getMinecraft().level == null) return;

        this.screen = screen;
        this.value = value;
        this.seen = false;
        this.active = true;
        this.setMessage(Component.literal("?"));
        switch (value)
        {
            case 1 -> entity = new Cow(EntityType.COW,screen.getMinecraft().level);
            case 2 -> entity = new Pig(EntityType.PIG,screen.getMinecraft().level);
            case 3 -> entity = new Chicken(EntityType.CHICKEN,screen.getMinecraft().level);
            case 4 -> entity = new Sheep(EntityType.SHEEP,screen.getMinecraft().level);
            case 5 -> entity = new Wolf(EntityType.WOLF,screen.getMinecraft().level);
            case 6 -> entity = new Cat(EntityType.CAT,screen.getMinecraft().level);
            case 7 -> entity = new Parrot(EntityType.PARROT,screen.getMinecraft().level);
            case 8 -> entity = new Bee(EntityType.BEE,screen.getMinecraft().level);
        }

    }

    @Override
    public boolean isHovered() {
        return super.isHovered() || screen.button == this;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int x, int y, float p_282542_) {
        Vector3f vec = new Vector3f();
        if (!active)
            super.renderWidget(graphics,x,y,p_282542_);
        if (this.seen || (!active))
        {
            super.renderWidget(graphics, x, y, p_282542_);
            InventoryScreen.renderEntityInInventory(graphics,(float) this.getX()+16, (float) this.getY()+28, 15f, vec,
                    new Quaternionf().rotationXYZ((float) Math.PI+.3f, (float) Math.PI/180*300, (float) 0),null,entity);
        }
        if (!this.seen && this.active)
        {
            ResourceLocation texture = ResourceLocation.withDefaultNamespace("textures/block/spruce_trapdoor.png");
            if (isHovered)
                graphics.blit(RenderType::guiTextured,texture,getX(),getY(),0,0,32,30,32,32);
            else
                graphics.blit(RenderType::guiTextured,texture,getX(),getY(),0,0,32,32,32,32);
        }
    }

    @Override
    public void onPress() {
        SoundManager manager = Minecraft.getInstance().getSoundManager();
        setMessage(Component.empty());
        //for (AbstractButton old:screen.list)
        //{
        //    //if (old != screen.button)
        //    //    //old.setMessage(Component.literal("?"));
        //}
        //this.setMessage(Component.literal(String.valueOf(value)));
        ResearchMinigamePairButton button = (ResearchMinigamePairButton) screen.button;
        if (screen.button == null)
        {
            for (AbstractButton old:screen.list)
            {
                ((ResearchMinigamePairButton) old).seen = false;
            }
            screen.button = this;
            this.seen = true;
            return;
        }
        this.seen = true;
        if (this == screen.button)
        {//we no longer have to explicitly check for bees
            //SoundEvent sound = entity.getType() == EntityType.BEE ? SoundEvents.BEE_HURT:entity.getAmbientSound();
            //assert sound != null;
            //manager.play(SimpleSoundInstance.forUI(sound,(float)Math.random()/4+.75f));
            entity.playAmbientSound();
            return;
        }
        int other = ((ResearchMinigamePairButton) screen.button).value;
        if (value == other)
        {//match
            this.active = false;
            button.active = false;
            boolean done = checkCompleted();
            screen.button = null;
            if (done)
            {
                screen.finish.visible = true;
                manager.play(SimpleSoundInstance.forUI(SoundEvents.PLAYER_LEVELUP,.5f));
                return;
            }
            manager.play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP,(float)Math.random()));
        }
        else
        {
            screen.button = null;
            setFocused(false);
            //manager.play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_TRAPDOOR_CLOSE,1f));
        }

    }
    boolean checkCompleted()
    {
        for (AbstractButton button: screen.list)
        {//false if a button is still active
            if (button.active)
                return false;
        }
        //true if all gone
        return true;
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
        soundManager.play(SimpleSoundInstance.forUI(SoundEvents.WOODEN_TRAPDOOR_CLOSE,1f));
    }

    @Override
    public boolean isHoveredOrFocused() {
        return false;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
