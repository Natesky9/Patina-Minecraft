package com.natesky9.patina.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.natesky9.patina.Screen.Research.ResearchMinigame;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.advancements.AdvancementTab;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;

public class ResearchWidget extends AbstractButton {
    private final AdvancementNode topic;
    private final AdvancementNode node;
    private final AdvancementsScreen advancementsScreen;
    private final ItemStack stack;
    private final int i;
    public ResearchWidget(int p_93365_, int p_93366_, Component p_93369_, AdvancementNode node, AdvancementsScreen screen,
                          AdvancementNode topic, int i) {
        super(p_93365_, p_93366_, 22, 22, p_93369_);
        this.node = node;
        this.advancementsScreen = screen;
        this.stack = node.holder().value().display().get().getIcon();
        this.topic = topic;
        this.i = i;
    }

    @Override
    public void onPress() {
        System.out.println("pressed");
        AdvancementWidget widget = advancementsScreen.getAdvancementWidget(node);
        AdvancementWidget research = advancementsScreen.getAdvancementWidget(topic);
        boolean hasUnlocked = widget.progress.isDone();
        boolean hasResearched = research.progress.getCriterion(node.holder().id().toString()).isDone();
        if (hasResearched)
            return;
        if (hasUnlocked)
        {
            ResearchMinigame minigame = new ResearchMinigame(topic, node);
            //minigame.init(Minecraft.getInstance(), 100, 100);
            ClientHooks.pushGuiLayer(Minecraft.getInstance(), minigame);
            return;
        }
        ClientHooks.popGuiLayer(Minecraft.getInstance());
        AdvancementTab tab = advancementsScreen.getTab(widget.advancementNode.root());
        Minecraft.getInstance().player.connection.getAdvancements().setSelectedTab(widget.advancementNode.root().holder(),true);
        tab.scroll(widget.getX(),widget.getY());
        //tab.scrollX = -Math.min(Math.max(widget.getX()-126,tab.minX),tab.maxX);//Math.max(-widget.getX(),tab.maxX);
        //tab.scrollY = -Math.min(Math.max(widget.getY()-70,tab.minY),tab.maxY);//Math.min(tab.scrollX,tab.minX);
        //tab.scrollY = //Math.max(-widget.getY(),tab.maxY);
        //tab.scrollY = //Math.min(tab.scrollY,tab.minY);

        //System.out.println(widget.getX() + ":" + widget.getY());

    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        AdvancementWidget widget = advancementsScreen.getAdvancementWidget(node);
        AdvancementWidget research = advancementsScreen.getAdvancementWidget(topic);
        if (widget == null || research == null) {
            return;
        }
        boolean hasUnlocked = widget.progress.isDone();
        boolean hasResearched = research.progress.getCriterion(node.holder().id().toString()).isDone();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        if (hasUnlocked)
        {//why did they choose such a strange color
            RenderSystem.setShaderColor((float) 185/255, (float) 143/255, (float) 44/255,1);
        }
        if (hasResearched)
        {
            RenderSystem.setShaderColor(1f,0f,0f,1f);
        }
        if (hasResearched && hasUnlocked)
        {
            RenderSystem.setShaderColor(0f,1f,0f,1f);
        }
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        graphics.blitSprite(RenderType::guiTextured,SPRITES.get(this.active, this.isHoveredOrFocused()),
                this.getX(), this.getY(), this.getWidth(), this.getHeight());
        RenderSystem.setShaderColor(1,1,1,1);
        graphics.renderFakeItem(stack, getX()+3, getY()+3);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
}
