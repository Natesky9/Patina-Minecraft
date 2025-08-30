package com.natesky9.patina.Screen.Research;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ResearchMinigameSequenceButton extends AbstractButton {
    ResearchMinigame screen;
    int value;
    boolean gimme = true;
    List<Item> tiers = List.of(Items.OAK_PLANKS,Items.COBBLESTONE,Items.COPPER_INGOT,Items.IRON_INGOT,
            Items.GOLD_INGOT, Items.EMERALD,Items.DIAMOND,Items.NETHERITE_INGOT);
    ItemStack stack;

    public ResearchMinigameSequenceButton(int x, int y, ResearchMinigame screen, int value) {
        super(x, y, 20,40, Component.empty());
        this.value = value;
        this.screen = screen;
        this.active = true;
        this.setMessage(Component.literal("?"));
        stack = tiers.get(value).getDefaultInstance();
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderWidget(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (getMessage().equals(Component.empty()))
            pGuiGraphics.renderFakeItem(stack,getX()+2,getY()+12);
    }

    void gimmeGuarantee()
    {//on first click, ensure it's always the first value
        screen.button = this;
        for (int i = 0; i < screen.list.size();i++)
        {
            ResearchMinigameSequenceButton search = (ResearchMinigameSequenceButton)screen.list.get(i);
            search.gimme = false;
        }
        if (value != 0)
        {//we switch with the start
            for (int i = 0; i < screen.list.size();i++)
            {
                ResearchMinigameSequenceButton search = (ResearchMinigameSequenceButton)screen.list.get(i);
                if (search.value == 0)
                {//swap em
                    System.out.println("switching :" + search.value + "/" + this.value);
                    search.stack = tiers.get(this.value).getDefaultInstance();
                    search.value = this.value;
                    this.value = 0;
                    this.stack = tiers.get(0).getDefaultInstance();
                    this.active = false;
                    this.setY(this.getY()-5);
                    return;
                }
            }
        }
    }

    @Override
    public void onPress() {
        SoundManager manager = Minecraft.getInstance().getSoundManager();
        this.setMessage(Component.empty());

        ResearchMinigameSequenceButton button = (ResearchMinigameSequenceButton) screen.button;
        if (button == this)
            return;
        if (button == null)
        {//start condition
            if (gimme)
            {//the first will always be the first
                gimmeGuarantee();
            }
            for (int i = 0; i < screen.list.size();i++)
            {
                button = (ResearchMinigameSequenceButton) screen.list.get(i);
                button.active = true;
                button.setY(100);
                button.setMessage(Component.literal("?"));
            }
            if (value != 0)
            {
                return;
            }
            this.active = false;
            this.setY(this.getY()-5);
            this.setMessage(Component.empty());
            screen.button = this;
            return;
        }
        int other = button.value;
        if (value != other+1)
        {//lose condition

            for (int i = 0; i < screen.list.size();i++)
            {
                button = (ResearchMinigameSequenceButton) screen.list.get(i);
                button.active = true;
                button.setY(100);
            }
            screen.button = null;
            return;
        }

        if (value == screen.list.size()-1)
        {//win condition
            screen.finish.visible = true;
            manager.play(SimpleSoundInstance.forUI(SoundEvents.PLAYER_LEVELUP,.5f));
        }
        screen.button = this;
        this.active = false;

        for (int i = 0; i < screen.list.size();i++)
        {

            button = (ResearchMinigameSequenceButton) screen.list.get(i);
            if (!button.active)
                button.setY(button.getY()-5);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
}
