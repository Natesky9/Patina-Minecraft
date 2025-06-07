package com.natesky9.patina.Screen;

import com.natesky9.patina.Menu.EvaporatorMenu;
import com.natesky9.patina.Menu.SieveMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SieveScreen extends AbstractContainerScreen<SieveMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/sieve.png");
    public SieveScreen(SieveMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, leftPos, topPos,
                0, 0, imageWidth, imageHeight, 256, 256);
    }
}
