package com.natesky9.patina.Screen;

import com.natesky9.patina.Menu.IceboxMenu;
import com.natesky9.patina.Patina;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class IceboxScreen extends AbstractContainerScreen<IceboxMenu> {
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Patina.MODID, "textures/gui/icebox.png");
    IceboxMenu icebox;
    public IceboxScreen(IceboxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        icebox = menu;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        guiGraphics.blit(RenderType::guiTextured, TEXTURE, leftPos, topPos,
                0, 0, this.imageWidth, this.imageHeight, 256, 256);
    }

    @Override
    protected void renderSlot(GuiGraphics guiGraphics, Slot slot) {
        super.renderSlot(guiGraphics, slot);
        //if (slot.index>=0 && slot.index < 20)
        //{
        //    ItemStack stack = slot.getItem();
        //    if (stack.getPopTime() > 0)
        //        stack.setPopTime(stack.getPopTime()-1);
        //}
    }

    @Override
    protected void renderSlotContents(GuiGraphics guiGraphics, ItemStack itemstack, Slot slot, @Nullable String countString) {

        int i = slot.x;
        int j = slot.y;
        int j1 = slot.x + slot.y * this.imageWidth;

        ItemStack stack = slot.getItem();
        float f = (float)stack.getPopTime() - minecraft.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate((float)(i + 8), (float)(j + 12), 0.0F);
            guiGraphics.pose().scale(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            guiGraphics.pose().translate((float)(-(i + 8)), (float)(-(j + 12)), 0.0F);
        }

        guiGraphics.renderItem(itemstack, i, j, j1);

        guiGraphics.renderItemDecorations(this.font, itemstack, i, j, countString);
    }
}
