package com.natesky9.patina.Event;

import com.natesky9.patina.Event.packets.FoundryTogglePacket;
import com.natesky9.patina.Event.packets.ResearchAdvancePacket;
import com.natesky9.patina.Event.packets.ResearchCreativeGrantPacket;
import com.natesky9.patina.Event.packets.SendParticlePacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketsEvent {
    public static void process(RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                FoundryTogglePacket.TYPE, FoundryTogglePacket.STREAM_CODEC,
                FoundryTogglePacket.ServerPayloadHandler::handleData);

        registrar.playToClient(
                SendParticlePacket.TYPE, SendParticlePacket.STREAM_CODEC,
                SendParticlePacket.ClientPayloadHandler::handleData);

        registrar.playToServer(
                ResearchCreativeGrantPacket.TYPE,ResearchCreativeGrantPacket.STREAM_CODEC,
                ResearchCreativeGrantPacket.ServerPayloadHandler::handleData);

        registrar.playToServer(
                ResearchAdvancePacket.TYPE,ResearchAdvancePacket.STREAM_CODEC,
                ResearchAdvancePacket.ServerPayloadHandler::handleData);
    }
}
