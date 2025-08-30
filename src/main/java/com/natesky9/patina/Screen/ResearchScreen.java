package com.natesky9.patina.Screen;

import com.natesky9.patina.Screen.Research.CriteriaWidget;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementNode;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ResearchScreen extends Screen {
    private static ResourceLocation TEXTURE = ResourceLocation.withDefaultNamespace("textures/gui/recipe_book.png");
    AdvancementNode research;
    AdvancementsScreen advancements;

    public ResearchScreen(AdvancementNode node, AdvancementsScreen screen) {
        super(node.advancement().display().get().getTitle());
        research = node;
        advancements = screen;
    }

    @Override
    protected void init() {
        AdvancementRequirements intermediate = research.advancement().requirements();
        AbstractWidget widget;
        Component component;
        int imageWidth = 148;
        int imageHeight = 167;
        int i = 0;
        int x = width/2-imageWidth/2;
        int y = height/2-imageHeight/2;
        ClientAdvancements client = minecraft.player.connection.getAdvancements();
        AbstractWidget back = Button.builder(CommonComponents.GUI_BACK, press ->
                {this.onClose();})
                .pos(140,200).build();
        addRenderableWidget(back);
        //don't render the requirements if it's already achieved
        boolean has = advancements.getAdvancementWidget(research).progress.isDone();
        if (has)
            return;
        //TODO:add a button and spread requirements among screens
        for (List<String> requirements: intermediate.requirements())
        {
            for (String string: requirements)
            {
                ResourceLocation location = ResourceLocation.parse(string);
                AdvancementHolder required = client.get(location);
                int widgetx = x + 26 * (i % 5) + 10;
                int widgety = y + imageHeight - 42 - (i / 5) * 32;
                if (required != null) {//if the requirement points to an advancement
                    AdvancementNode node = client.getTree().get(required);
                    //only add widgets for advancements that exist?
                    component = Component.literal(string);
                    widget = new ResearchWidget(widgetx, widgety, component, node, advancements, research, i);
                    addRenderableWidget(widget);
                    i++;
                    continue;
                }

                widget = new CriteriaWidget(widgetx,widgety,advancements,research,string);
                addRenderableWidget(widget);
                i++;
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int p_281550_, int p_282878_, float p_282465_) {
        int imageWidth = 148;
        int imageHeight = 167;
        int x = (width/2)-imageWidth/2;
        int y = (height/2)-(imageHeight+17)/2;
        //render background
        //TODO place the background in the actual background method
        this.renderBlurredBackground();
        graphics.blit(RenderType::guiTextured,TEXTURE, x, y, 0, 0, 256, 256,256,256);
        //super.render(graphics, p_281550_, p_282878_, p_282465_);
        for (Renderable renderable : this.renderables) {
            renderable.render(graphics, p_281550_, p_282878_, p_282465_);
        }

        ItemStack display = research.advancement().display().get().getIcon();
        graphics.renderFakeItem(display, width/2-8, y +28);
        Component title = research.advancement().display().get().getTitle();
        graphics.drawString(font,title,x+16,y+16,-1);
        Component desc = research.advancement().display().get().getDescription();
        //region split and draw text
        StringSplitter stringsplitter = font.getSplitter();
        List<FormattedText> list = List.of();
        int[] TEST_SPLIT_OFFSETS = new int[]{0,0,0,0,0};
        for (int i : TEST_SPLIT_OFFSETS) {
            list = stringsplitter.splitLines(desc, 120 - i, Style.EMPTY);

        }
        List<FormattedCharSequence> order = Language.getInstance().getVisualOrder(list);
        for (int i = 0; i < order.size(); i++)
        {
            graphics.drawString(font, order.get(i),x+16,y+48+12*i,-1);
        }
        //endregion
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
