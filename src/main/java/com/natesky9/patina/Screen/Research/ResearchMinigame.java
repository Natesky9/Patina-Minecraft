package com.natesky9.patina.Screen.Research;

import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResearchMinigame extends Screen {
    AdvancementNode topic;
    AdvancementNode node;
    ResearchMinigameFinish finish;
    AbstractButton button;
    List<AbstractButton> list = new ArrayList<>();
    int type = 0;
    ResourceLocation background = ResourceLocation.withDefaultNamespace("textures/block/dark_oak_planks.png");

    public ResearchMinigame(AdvancementNode topic, AdvancementNode node) {
        super(Component.literal("Minigame"));
        this.topic = topic;
        this.node = node;
        width = Minecraft.getInstance().getWindow().getWidth();
        height = Minecraft.getInstance().getWindow().getHeight();
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.renderBackground(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        pGuiGraphics.blit(RenderType::guiTextured,background,100,32,0,0,240,150,32,32);
    }

    void initMatchTwo()
    {
        //generate the match 2
        int top = 40;
        int left = 150;
        ResearchMinigamePairButton pairButton;
        for (int i = 0; i < 16; i++)
        {
            int value = (i/2)+1;
            pairButton = new ResearchMinigamePairButton(0,0,this,value);

            pairButton.value =value;
            list.add(pairButton);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 16; i++)
        {
            pairButton = (ResearchMinigamePairButton) list.get(i);
            pairButton.setX((i%4) * 32+left);
            pairButton.setY((i/4) * 32+top);
            addRenderableWidget(pairButton);
        }
    }
    void initSequence()
    {
        int startx = 120;
        int starty = 100;
        ResearchMinigameSequenceButton sequenceButton;
        for (int i = 0; i < 8; i++)
        {
            sequenceButton = new ResearchMinigameSequenceButton(0,0,this,i);

            list.add(sequenceButton);
        }
        Collections.shuffle(list);
        for (int i = 0; i < 8; i++)
        {
            sequenceButton = (ResearchMinigameSequenceButton) list.get(i);
            sequenceButton.setX(i*24+startx);
            sequenceButton.setY(starty);
            addRenderableWidget(sequenceButton);
        }
    }
    void initColorChain()
    {
        ResearchMinigameColorChainButton fresh;
        List<Item> colors = ResearchMinigameColorChainButton.colors;
        for (int i = 0;i < colors.size();i++)
        {//create the palette
            fresh = new ResearchMinigameColorChainButton(110+20*(i/4), 60+20*(i%4), -1,
                    this, true);
            fresh.set(colors.get(i));
            this.addRenderableWidget(fresh);
        }

        colors = new ArrayList<>(ResearchMinigameColorChainButton.colors);
        int index = 0;
        Collections.shuffle(colors);
        for (int x = 0; x < 5;x++) {//double loop, for her pleasure
            for (int y = 0; y < 5; y++) {

                //blank button or color button
                fresh = new ResearchMinigameColorChainButton(210 + 24 * x, 50 + 24 * y,
                        x*5+y,this, false);
                //x and y look reversed in the index here but trust me they're not
                this.addRenderableWidget(fresh);
                this.list.add(fresh);

                if ((x == 0 || x == 4) && (y == 0 || y == 4))
                {//visit all 4 corners
                    fresh.start = true;
                    fresh.set(colors.get(index));
                    index++;
                }
            }
        }

    }
    void initCity()
    {
        for (int x = 0;x < 5;x++)
        {
            for (int y = 0;y < 5;y++)
            {
                ResearchMinigameCity button = new ResearchMinigameCity(120+x*32,40+y*32,this,x*5+y);
                list.add(button);
                addRenderableWidget(button);
            }
        }
        List<AbstractButton> copy = new ArrayList<>(List.copyOf(list));
        Collections.shuffle(copy);
        //scramble!
        for (int i = 0;i < 10;i++)
        {
            ResearchMinigameCity button = (ResearchMinigameCity) copy.get(i);
            button.neighbors(button.index);
        }
    }
    @Override
    protected void init() {
        type = (int)(Math.random()*4);
        button = null;
        switch (type)
        {
            default -> initMatchTwo();
            case 1 -> initSequence();
            case 2 -> initColorChain();
            case 3 -> initCity();
        }
        finish = new ResearchMinigameFinish(140,200,this);
        this.addRenderableWidget(finish);
        finish.visible = false;
        //back button to return to previous screen
        AbstractWidget back = Button.builder(CommonComponents.GUI_BACK, press ->
                {
                    this.onClose();//replace with poplayer?
                })
                .pos(300,200).size(40,20).build();
        addRenderableWidget(back);
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    @Override
    public void onClose() {
        super.onClose();
        //ModPackets.sendToServer(new PacketResearchAdvance(node.holder().id(), index));
    }
}
