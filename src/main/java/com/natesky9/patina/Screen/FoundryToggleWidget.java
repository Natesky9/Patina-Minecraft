package com.natesky9.patina.Screen;

import com.natesky9.patina.Blocks.MachineFoundryBlock;
import com.natesky9.patina.Event.packets.FoundryTogglePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class FoundryToggleWidget extends AbstractButton {
    GlobalPos pos;
    FoundryScreen screen;
    public FoundryToggleWidget(FoundryScreen foundryScreen, int x, int y, int width, int height, GlobalPos position) {
        super(x, y, width, height, Component.empty());
        pos = position;
        screen = foundryScreen;
    }

    @Override
    public void onPress() {
        SoundManager manager = Minecraft.getInstance().getSoundManager();
        manager.play(SimpleSoundInstance.forUI(SoundEvents.LEVER_CLICK,1F));
        PacketDistributor.sendToServer(new FoundryTogglePacket(pos));
    }


    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        Level level = screen.getMenu().foundry.getLevel();
        BlockPos pos = screen.getMenu().foundry.getBlockPos();
        boolean mode = level.getBlockState(pos).getValue(MachineFoundryBlock.MODE);
        guiGraphics.blitSprite(RenderType::guiTextured, SPRITES.get(mode, this.isHoveredOrFocused()),
                this.getX(), this.getY(), this.getWidth(), this.getHeight(), ARGB.white(this.alpha));

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
