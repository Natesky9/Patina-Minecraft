package com.natesky9.patina.Event;

import com.natesky9.patina.Event.packets.FoundryTogglePacket;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class PacketsEvent {
    public static void process(RegisterPayloadHandlersEvent event)
    {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                FoundryTogglePacket.TYPE, FoundryTogglePacket.STREAM_CODEC,
                FoundryTogglePacket.ServerPayloadHandler::handleData);
    }
}
