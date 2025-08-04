package com.natesky9.patina.Screen;

import com.natesky9.patina.Menu.AlembicMenu;
import com.natesky9.patina.Menu.WardrobeMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class WardrobeScreen extends AbstractContainerScreen<WardrobeMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/wardrobe.png");
    public WardrobeScreen(WardrobeMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int mouseX, int mouseY) {
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, leftPos, topPos,
                0, 0, this.imageWidth, this.imageHeight, 256, 256);

        if (this.minecraft != null && this.minecraft.player != null)
        {
            int x = (width-imageWidth)/2;
            int y = (height-imageHeight)/2;
            InventoryScreen.renderEntityInInventoryFollowsMouse(guiGraphics,x+26,y+8,x+75,y+78,30,
                    0.0625F,mouseX,mouseY,this.minecraft.player);
        }
    }
}
