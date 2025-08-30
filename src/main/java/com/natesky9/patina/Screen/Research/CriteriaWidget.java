package com.natesky9.patina.Screen.Research;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class CriteriaWidget extends AbstractButton {
    AdvancementsScreen van;
    AdvancementNode node;
    String string;
    public CriteriaWidget(int p_93365_, int p_93366_, AdvancementsScreen screen, AdvancementNode node, String string) {
        super(p_93365_, p_93366_, 22, 22, Component.literal("???"));
        this.van = screen;
        this.node = node;
        this.string = string;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int p_282682_, int p_281714_, float p_282542_) {
        Minecraft minecraft = Minecraft.getInstance();

        AdvancementWidget widget = van.getAdvancementWidget(node);
        boolean has = widget.progress.getCriterion(string).isDone();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        if (has)
            RenderSystem.setShaderColor(0,1,0,1);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        graphics.blitSprite(RenderType::guiTextured,SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.getFGColor();
        this.renderString(graphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

    @Override
    public void onPress() {
        String output = string.replace("_"," ").replace("minecraft:","");
        setMessage(Component.literal(output));
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
