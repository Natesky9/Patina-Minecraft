package com.natesky9.patina.Block.ApplianceResearchDesk;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ResearchMinigameCity extends AbstractButton {
    ResearchMinigame screen;
    boolean on = true;
    int index;
    ResourceLocation lamp_on = ResourceLocation.withDefaultNamespace("textures/block/redstone_lamp_on.png");
    ResourceLocation lamp_off = ResourceLocation.withDefaultNamespace("textures/block/redstone_lamp.png");
    public ResearchMinigameCity(int pX, int pY, ResearchMinigame screen, int index) {
        super(pX, pY, 32,32, Component.empty());
        this.screen = screen;
        this.index = index;
    }

    @Override
    public void onPress() {
        neighbors(index);

        for (int i=0;i < screen.list.size();i++)
        {
            ResearchMinigameCity button = (ResearchMinigameCity) screen.list.get(i);
            if (!button.on)
                return;
        }
        //if all buttons on
        screen.finish.visible = true;
    }
    public void neighbors(int index)
    {
        get(index).on = !get(index).on;

        int north = (index % 5) != 0 ? index-1:-1;
        int east = (index) < 20 ? index+5:-1;
        int south = (index % 5) != 4 ? index+1:-1;
        int west = (index) > 4 ? index-5:-1;

        if (north != -1)
            get(north).on = !get(north).on;
        if (south != -1)
            get(south).on = !get(south).on;
        if (east != -1)
            get(east).on = !get(east).on;
        if (west != -1)
            get(west).on = !get(west).on;

    }
    ResearchMinigameCity get(int index)
    {
        return (ResearchMinigameCity) screen.list.get(index);
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {

        //RenderSystem.setShaderTexture(0,lamp_on);
        if (on)
        {
            graphics.blit(lamp_on,getX(),getY(),0,0,32,32);
        }
        else
            graphics.blit(lamp_off,getX(),getY(),0,0,32,32);
        //super.renderWidget(graphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
