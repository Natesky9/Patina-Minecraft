package com.natesky9.patina.Screen;

import com.natesky9.patina.Blocks.Research.ResearchMenu;
import com.natesky9.patina.Event.packets.ResearchCreativeGrantPacket;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementWidget;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.Iterator;

public class ResearchDeskScreen extends AbstractContainerScreen<ResearchMenu> {
    private static final Component TITLE = Component.literal("Research");
    Player player;
    ClientAdvancements advancements;
    Screen research;
    AdvancementsScreen advancementsScreen;

    public ResearchDeskScreen(ResearchMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        player = playerInventory.player;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {

    }

    @Override
    protected void init() {
        super.init();
        advancements = this.minecraft.player.connection.getAdvancements();
        advancementsScreen = new AdvancementsScreen(this.advancements);
        advancementsScreen.init(minecraft,width,height);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partial) {
        super.render(graphics, mouseX, mouseY, partial);
        advancementsScreen.render(graphics, mouseX, mouseY, partial);

        if (advancementsScreen.selectedTab != null)
        {
            int i = (width-252)/2;
            int j = (height-140)/2;
            int a = Mth.floor(advancementsScreen.selectedTab.scrollX)+i+9;
            int b = Mth.floor(advancementsScreen.selectedTab.scrollY)+j+18;
            Iterator<AdvancementWidget> iterator = advancementsScreen.selectedTab.widgets.values().iterator();
            while(iterator.hasNext())
            {
                AdvancementWidget widget = iterator.next();
                if (widget.isMouseOver(a,b,mouseX,mouseY) && minecraft.player.isCreative())
                {
                    graphics.drawString(font, "Hold alt to grant", 4,64,16777215);
                }
            }
        }
    }

    boolean insideAdvancementScreen(int mouseX, int mouseY)
    {
        //TODO: change magic numbers to vars
        int i = (this.width - 252) /2;
        int j = (this.height - 140) /2;
        return (mouseX > i+9 && mouseX < i+234+9 && mouseY > j+18 && mouseY < j+113+18);
    }
    boolean clickAdvancements(double x, double y)
    {
        int mouseX = (int)x;
        int mouseY = (int)y;
        //i and j are the top left of the screen
        int i = (advancementsScreen.width - 252) / 2;
        int j = (advancementsScreen.height - 140) / 2;

        int a = Mth.floor(advancementsScreen.selectedTab.scrollX)+i+9;
        int b = Mth.floor(advancementsScreen.selectedTab.scrollY)+j+18;
        if (!insideAdvancementScreen(mouseX, mouseY)) return false;
        //we've clicked inside the advancements
        Iterator<AdvancementWidget> var9 = advancementsScreen.selectedTab.widgets.values().iterator();
        while(var9.hasNext())
        {
            AdvancementWidget widget = var9.next();
            if (widget.isMouseOver(a, b,mouseX,mouseY))
            {
                AdvancementNode node = widget.advancementNode;
                if (player.isCreative() && hasAltDown())
                {//if creative, grant the advancement
                    //System.out.println(node);
                    System.out.println("granting: " + node.holder().id());
                    PacketDistributor.sendToServer(new ResearchCreativeGrantPacket(node.holder().id()));
                    return true;
                }
                research = new ResearchScreen(node,advancementsScreen);
                research.init(minecraft,width,height);
                ClientHooks.pushGuiLayer(minecraft,research);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {
        if (button == 0)
        {
            if (advancementsScreen.mouseClicked(x,y,button) || clickAdvancements(x,y)) return super.mouseClicked(x,y,button);
        }
        return super.mouseClicked(x,y,button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return advancementsScreen.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseScrolled(double p_364830_, double p_360707_, double p_364436_, double p_364417_) {
        return advancementsScreen.mouseScrolled(p_364830_, p_360707_, p_364436_, p_364417_);
    }
}
