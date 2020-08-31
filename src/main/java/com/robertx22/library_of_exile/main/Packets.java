package com.robertx22.library_of_exile.main;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.server.PlayerStream;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Packets {

    public static <T> void sendToClient(PlayerEntity player, MyPacket<T> packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.saveToData(buf);
        ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, packet.getIdentifier(), buf);
    }

    public static <T> void sendToServer(MyPacket<T> packet) {
        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
        packet.saveToData(buf);
        ClientSidePacketRegistry.INSTANCE.sendToServer(packet.getIdentifier(), buf);
    }

    public static <T> void registerClientToServerPacket(MyPacket<T> packet) {
        ServerSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

    public static <T> void registerServerToClient(MyPacket<T> packet) {
        ClientSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

    public static void sendToTracking(MyPacket msg, Entity entity) {
        if (entity.world.isClient) {

        } else {
            sendToTracking(msg, entity.getBlockPos(), entity.world);
        }
    }

    public static void sendToTracking(MyPacket msg, BlockPos pos, World world) {
        if (world.isClient) {

        } else {

            if (msg == null || world == null) {
                return;
            }
            PlayerStream.watching(world, pos)
                .forEach(x -> {
                    Packets.sendToClient(x, msg);
                });
        }
    }

}
