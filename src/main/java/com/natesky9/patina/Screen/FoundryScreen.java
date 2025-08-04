package com.natesky9.patina.Screen;

import com.natesky9.patina.Menu.FoundryMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FoundryScreen extends AbstractContainerScreen<FoundryMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/foundry.png");
    public FoundryScreen(FoundryMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        imageWidth = 176;
        imageHeight = 176;
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
    }
}
