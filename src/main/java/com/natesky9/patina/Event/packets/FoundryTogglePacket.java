package com.natesky9.patina.Event.packets;

import com.natesky9.patina.Blocks.MachineFoundryBlock;
import com.natesky9.patina.Patina;
import com.natesky9.patina.init.ModBlocks;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.spongepowered.asm.mixin.Final;

public record FoundryTogglePacket(GlobalPos pos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<FoundryTogglePacket> TYPE = new CustomPacketPayload.Type<>(
            ResourceLocation.fromNamespaceAndPath(Patina.MODID,"foundry_toggle"));
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static final StreamCodec<ByteBuf,FoundryTogglePacket> STREAM_CODEC =
            StreamCodec.composite(GlobalPos.STREAM_CODEC,FoundryTogglePacket::pos,FoundryTogglePacket::new);
    //
    public static class ServerPayloadHandler
    {
        public static void handleData(final FoundryTogglePacket packet, final IPayloadContext context)
        {
            GlobalPos location = packet.pos;
            MinecraftServer server = context.player().getServer();
            if (server == null)
                return;
            ServerLevel level = server.getLevel(location.dimension());
            if (level == null)
                return;
            BlockPos pos = location.pos();
            BlockState state = level.getBlockState(pos);
            boolean mode = state.getValue(MachineFoundryBlock.MODE);
            //invert the mode
            level.setBlockAndUpdate(pos,state.setValue(MachineFoundryBlock.MODE,!mode));
        }
    }
}
