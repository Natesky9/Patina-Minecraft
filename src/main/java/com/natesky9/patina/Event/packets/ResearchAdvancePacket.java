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

public record ResearchAdvancePacket(ResourceLocation string, ResourceLocation name) implements CustomPacketPayload{

    public static final CustomPacketPayload.Type<ResearchAdvancePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Patina.MODID,"research_advance"));
    public static final StreamCodec<ByteBuf,ResearchAdvancePacket> STREAM_CODEC =
            StreamCodec.composite(ResourceLocation.STREAM_CODEC,ResearchAdvancePacket::string,
                    ResourceLocation.STREAM_CODEC, ResearchAdvancePacket::name,
                    ResearchAdvancePacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class ServerPayloadHandler
    {
        public static void handleData(final ResearchAdvancePacket packet, final IPayloadContext context)
        {
            //System.out.println("Granting a criteria");
            ServerPlayer player = (ServerPlayer) context.player();
            PlayerAdvancements advancements = player.getAdvancements();
            //
            ServerAdvancementManager manager = player.getServer().getAdvancements();
            AdvancementHolder holder = manager.get(packet.string);
            //
            Advancement advancement = manager.tree().get(holder).advancement();
            advancements.award(holder, packet.name.toString());
            advancements.flushDirty(player);
        }
    }
}
