package com.natesky9.patina.Screen.Research;

import com.natesky9.patina.Event.packets.ResearchAdvancePacket;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.CommonComponents;
import net.neoforged.neoforge.network.PacketDistributor;

public class ResearchMinigameFinish extends AbstractButton {
    ResearchMinigame screen;
    public ResearchMinigameFinish(int pX, int pY, ResearchMinigame screen) {
        super(pX, pY, 128, 20, CommonComponents.GUI_CONTINUE);
        this.screen = screen;
    }

    @Override
    public void onPress() {
        PacketDistributor.sendToServer(new ResearchAdvancePacket(screen.topic.holder().id(), screen.node.holder().id()));
        screen.onClose();
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
