package com.natesky9.patina.Screen.Research;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class ResearchMinigameColorChainButton extends AbstractButton {
    public static final List<Item> colors = List.of(
            Items.RED_DYE,Items.ORANGE_DYE,Items.YELLOW_DYE,Items.GREEN_DYE,Items.BLUE_DYE,Items.PURPLE_DYE,
            Items.MAGENTA_DYE,Items.PINK_DYE,Items.BROWN_DYE,Items.WHITE_DYE,Items.GRAY_DYE,Items.BLACK_DYE,
            Items.LIME_DYE,Items.LIGHT_BLUE_DYE,Items.CYAN_DYE,Items.LIGHT_GRAY_DYE
    );
    static final Map<Item, Pair<Item,Item>> components = Map.ofEntries(
            entry(Items.ORANGE_DYE,new Pair<>(Items.RED_DYE,Items.YELLOW_DYE)),
            entry(Items.GREEN_DYE,new Pair<>(Items.YELLOW_DYE,Items.BLUE_DYE)),
            entry(Items.PURPLE_DYE,new Pair<>(Items.RED_DYE,Items.BLUE_DYE)),
            entry(Items.PINK_DYE,new Pair<>(Items.RED_DYE,Items.WHITE_DYE)),
            entry(Items.LIME_DYE,new Pair<>(Items.GREEN_DYE,Items.WHITE_DYE)),
            entry(Items.LIGHT_BLUE_DYE,new Pair<>(Items.BLUE_DYE,Items.WHITE_DYE)),
            entry(Items.MAGENTA_DYE,new Pair<>(Items.BLUE_DYE,Items.PINK_DYE)),
            entry(Items.GRAY_DYE,new Pair<>(Items.BLACK_DYE,Items.WHITE_DYE)),
            entry(Items.BROWN_DYE,new Pair<>(Items.ORANGE_DYE,Items.BLACK_DYE)),
            entry(Items.CYAN_DYE,new Pair<>(Items.GREEN_DYE,Items.BLUE_DYE)),
            entry(Items.LIGHT_GRAY_DYE,new Pair<>(Items.GRAY_DYE,Items.WHITE_DYE))
    );
    static final Pair<Item,Item> empty = new Pair<>(Items.AIR,Items.AIR);
    ResearchMinigame screen;
    Item value = null;
    boolean swatch;
    boolean start = false;
    int index;
    ItemStack stack;
    boolean connected;

    public ResearchMinigameColorChainButton(int p_93365_, int p_93366_, int index, ResearchMinigame screen, boolean swatch) {
        super(p_93365_, p_93366_, 20,20, Component.empty());
        this.index = index;
        this.screen = screen;
        //this.value = color;
        this.swatch = swatch;
        //this.start = start;
        //if (value != null)
        //    this.stack = value.getDefaultInstance();
        //setMessage(Component.literal(String.valueOf(index)));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (pButton == 0)
            return super.mouseClicked(pMouseX, pMouseY, pButton);

        if (pButton == 1 && isHovered())
        {
            if (start)
                return true;
            this.value = null;
            this.stack = null;
            //clear
            for (int i = 0;i < screen.list.size();i++)
            {
                get(i).connected = false;
            }
            return true;
        }
        return false;
    }

    @Override
    public void playDownSound(SoundManager pHandler) {
        if (start)
            return;
        if (swatch)
        {
            pHandler.play(SimpleSoundInstance.forUI(SoundEvents.UI_LOOM_TAKE_RESULT,.5f));
            return;
        }
        pHandler.play(SimpleSoundInstance.forUI(SoundEvents.WOOL_PLACE,.5f));

    }

    @Override
    public void onPress() {
        if (swatch)
        {
            screen.button = this;
            return;
        }
        if (start)
            return;
        if (screen.button == null)
            return;
        set(((ResearchMinigameColorChainButton)screen.button).value);

        empty();
        fill(index);
        check();
    }
    void check()
    {
        ResearchMinigameColorChainButton one = get(0);
        ResearchMinigameColorChainButton two = get(4);
        ResearchMinigameColorChainButton three = get(20);
        ResearchMinigameColorChainButton four = get(24);

        if (one.connected && two.connected && three.connected && four.connected)
        {
            SoundManager manager = Minecraft.getInstance().getSoundManager();
            manager.play(SimpleSoundInstance.forUI(SoundEvents.PLAYER_LEVELUP,.5f));
            screen.finish.visible = true;
        }
    }
    ResearchMinigameColorChainButton get(int index)
    {
        return (ResearchMinigameColorChainButton)screen.list.get(index);
    }
    void empty()
    {
        for (int i = 0;i < screen.list.size();i++)
        {
            get(i).connected = false;
        }
    }
    void recursive_fill(ResearchMinigameColorChainButton button,int index)
    {
        if (index != -1)
        {
            ResearchMinigameColorChainButton north_button = get(index);
            if (neighborMatches(north_button.value,button.value))
                fill(index);
        }
    }
    void fill(int index)
    {
        ResearchMinigameColorChainButton button = get(index);
        if (button.value == null)
            return;
        if (button.connected)
            return;
        button.connected = true;
        int north = (index % 5) != 0 ? index-1:-1;
        int east = (index) < 20 ? index+5:-1;
        int south = (index % 5) != 4 ? index+1:-1;
        int west = (index) > 4 ? index-5:-1;
        recursive_fill(button,north);
        recursive_fill(button,east);
        recursive_fill(button,south);
        recursive_fill(button,west);
    }
    public void set(Item item)
    {//update the itemstack
        value = item;
        if (this.value != null)
            this.stack = value.getDefaultInstance();
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partial) {
        super.renderWidget(graphics,mouseX,mouseY,partial);
        //graphics.pose().rotateAround(null,0f,0f,1f);
        if (stack != null)
        {
            graphics.renderFakeItem(stack,this.getX()+2,this.getY()+2);
            if (connected)
                graphics.renderFakeItem(stack,this.getX()+2,this.getY()+4);

            if ((index % 5) != 4 && index != -1)
            {//draw lines for all but right side
                Item neighbor = get(index+1).value;
                //draw horizontal lines
                if (neighborMatches(neighbor,value))
                    graphics.vLine(getX()+10,getY()+20,getY()+24, -1);
            }
            if ((index / 20 < 1) && index != -1)
            {//draw lines for all but bottom side
                Item neighbor = get(index+5).value;
                if (neighborMatches(neighbor,value))
                    graphics.hLine(getX()+20,getX()+24,getY()+10, -1);
            }
        }
    }
    boolean neighborMatches(Item neighbor, Item self)
    {
        if (neighbor == null)
            return false;
        boolean geta = components.getOrDefault(self,empty).getA() == neighbor;
        boolean getb = components.getOrDefault(self,empty).getB() == neighbor;
        boolean bget = components.getOrDefault(neighbor,empty).getA() == self;
        boolean aget = components.getOrDefault(neighbor,empty).getB() == self;

        return (geta||getb||aget||bget);
    }

    @Override
    public boolean isFocused() {
        return screen.button == this;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput p_259858_) {

    }
}
