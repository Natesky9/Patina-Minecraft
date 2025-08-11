package com.natesky9.patina.Event.packets;

import com.natesky9.patina.Patina;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SendParticlePacket(ParticleOptions options, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SendParticlePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Patina.MODID,"send_particle"));
    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static final StreamCodec<RegistryFriendlyByteBuf,SendParticlePacket> STREAM_CODEC =
            StreamCodec.composite(ParticleTypes.STREAM_CODEC,
                    SendParticlePacket::options,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::x,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::y,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::z,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::xSpeed,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::ySpeed,
                    ByteBufCodecs.DOUBLE,
                    SendParticlePacket::zSpeed,
                    SendParticlePacket::new);
    //
    public static class ClientPayloadHandler
    {
        public static void handleData(final SendParticlePacket packet, final IPayloadContext context)
        {
            Level level = context.player().level();
            level.addParticle(packet.options, packet.x, packet.y, packet.z,
                    packet.xSpeed,packet.ySpeed,packet.zSpeed);

        }
    }
}
