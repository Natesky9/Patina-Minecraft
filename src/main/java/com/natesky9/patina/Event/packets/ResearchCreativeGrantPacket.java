package com.natesky9.patina.Event.packets;

import com.natesky9.patina.Patina;
import io.netty.buffer.ByteBuf;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record ResearchCreativeGrantPacket(ResourceLocation advancement) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ResearchCreativeGrantPacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Patina.MODID,"research_creative_grant"));
    public static final StreamCodec<ByteBuf,ResearchCreativeGrantPacket> STREAM_CODEC =
            StreamCodec.composite(ResourceLocation.STREAM_CODEC,ResearchCreativeGrantPacket::advancement,ResearchCreativeGrantPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ServerPayloadHandler
    {
        public static void handleData(final ResearchCreativeGrantPacket packet, final IPayloadContext context)
        {

            ServerPlayer player = (ServerPlayer) context.player();
            PlayerAdvancements advancements = player.getAdvancements();
            //
            ServerAdvancementManager manager = player.getServer().getAdvancements();
            AdvancementHolder holder = manager.get(packet.advancement);
            //not criteria
            String done = manager.tree().get(holder).advancement().requirements().toString();
            System.out.println(done);
            //
            Advancement advancement = manager.tree().get(holder).advancement();
            for (List<String> requirements: advancement.requirements().requirements())
            {
                for (String string: requirements)
                {
                    //System.out.println("granting: " + string);
                    advancements.award(holder,string);
                }
            }
        }
    }
}
