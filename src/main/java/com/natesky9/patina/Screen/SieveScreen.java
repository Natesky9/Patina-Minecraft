package com.natesky9.patina.Screen;

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
        if (menu.sieve.isWaterLogged())
            guiGraphics.blit(RenderType::guiTextured,TEXTURE,leftPos+41,topPos+57,176,0,12,19,256,256);
    }


    @Override
    public void render(GuiGraphics p_283479_, int p_283661_, int p_281248_, float p_281886_) {
        super.render(p_283479_, p_283661_, p_281248_, p_281886_);
    }
}
