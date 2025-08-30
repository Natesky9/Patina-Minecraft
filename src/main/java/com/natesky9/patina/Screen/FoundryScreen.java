package com.natesky9.patina.Screen;

import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.Level;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/foundry.png");
    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 176;
        titleLabelX = 26;
        titleLabelY = 6;
    }

    @Override
    protected void init() {
        super.init();
        GlobalPos position = new GlobalPos(menu.foundry.getLevel().dimension(),menu.foundry.getBlockPos());
        addRenderableWidget(new FoundryToggleWidget(this,leftPos+imageWidth-16-8,topPos+8,16,16,position));
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, leftPos, topPos,
                0, 0, this.imageWidth, this.imageHeight, 256, 256);

        Level level = menu.foundry.getLevel();
        BlockPos pos = menu.foundry.getBlockPos();
        boolean alloy = menu.foundry.mode();
        if (alloy)
            guiGraphics.blit(RenderType::guiTextured,TEXTURE,leftPos+46,topPos+26,0,176,101,26,256,256);
        else
            guiGraphics.blit(RenderType::guiTextured,TEXTURE,leftPos+46,topPos+26,0,202,101,26,256,256);

        int heat = getHeat();
        int heatMax = getHeatMax();
        int progress = getProgress();
        int progressMax = getProgressMax();
        int barHeight = (int)Math.clamp(Math.ceil(heat/(float)heatMax*36),0,36);
        int barWidth = (int)Math.clamp(Math.ceil(progress/(float)progressMax*54),0,54);
        int startY = topPos+6+36-barHeight;

        guiGraphics.blit(RenderType::guiTextured,TEXTURE,leftPos+6,startY,176,0,16,barHeight,256,256);
        guiGraphics.blit(RenderType::guiTextured,TEXTURE,leftPos+67,topPos+57,101,176,barWidth,15,256,256);

    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float p_281886_) {
        super.render(graphics, mouseX, mouseY, p_281886_);
        //graphics.drawString(font,heat + "/" + heatMax, leftPos,topPos+32, ARGB.white(1));
        //graphics.drawString(font,(int)((float)getProgress()/(float)getProgressMax()*100) + "%",leftPos,topPos+64,ARGB.white(1));
        renderTooltip(graphics,mouseX,mouseY);
    }

    int getHeat()
    {
        return menu.data.get(0);
    }
    int getHeatMax()
    {
        return menu.data.get(1);
    }
    int getProgress()
    {
        return menu.data.get(2);
    }
    int getProgressMax()
    {
        return menu.data.get(3);
    }
}
