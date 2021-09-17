package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.registers.PacketChannel;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;

public class Packets {

    public static <T> void sendToClient(PlayerEntity player, MyPacket<T> packet) {
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        packet.saveToData(buf);

        PacketChannel.INSTANCE.sendTo(
            packet,
            ((ServerPlayerEntity) player).connection.getConnection(),
            NetworkDirection.PLAY_TO_CLIENT
        );
    }

    public static <T> void sendToServer(MyPacket<T> packet) {
        PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
        packet.saveToData(buf);

        PacketChannel.INSTANCE.sendToServer(packet);

    }

    public static <T> void registerClientToServerPacket(MyPacket<T> packet) {

        PacketChannel.INSTANCE.registerMessage(
            PacketChannel.CLIENT_ID++,
            (Class<MyPacket<T>>) packet.getClass(), // todo
            MyPacket::saveToData,
            packet::loadFromDataUSETHIS,
            MyPacket::handle
        );
    }

    public static <T> void registerServerToClient(MyPacket<T> packet) {

        //PacketChannel.INSTANCE.registerMessage()

        PacketChannel.INSTANCE.registerMessage(
            PacketChannel.SERVER_ID++,
            (Class<MyPacket<T>>) packet.getClass(), // todo
            MyPacket::saveToData,
            packet::loadFromDataUSETHIS,
            MyPacket::handle
        );

        // ClientSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

    public static void sendToTracking(MyPacket msg, BlockPos pos, World world) {
        PacketChannel.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), msg);
    }

    public static void sendToTracking(MyPacket msg, Entity en) {
        if (en.level.isClientSide) {

        } else {

            if (msg == null) {
                return;
            }

            PacketChannel.INSTANCE.send(PacketDistributor.TRACKING_ENTITY.with(() -> en), msg);

        }
    }

}
