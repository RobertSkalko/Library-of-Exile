package com.robertx22.library_of_exile.main;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.HashMap;

public class Packets {

    static HashMap<ResourceLocation, SimpleChannel> channels = new HashMap<>();

    public static <T> void sendToClient(PlayerEntity player, MyPacket<T> packet) {
        try {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            packet.saveToData(buf);

            channels.get(packet.getIdentifier())
                .sendTo(
                    packet,
                    ((ServerPlayerEntity) player).connection.getConnection(),
                    NetworkDirection.PLAY_TO_CLIENT
                );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void sendToServer(MyPacket<T> packet) {
        try {
            PacketBuffer buf = new PacketBuffer(Unpooled.buffer());
            packet.saveToData(buf);

            channels.get(packet.getIdentifier())
                .sendToServer(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> void registerClientToServerPacket(SimpleChannel channel, MyPacket<T> packet, int id) {
        channels.put(packet.getIdentifier(), channel);

        channel.registerMessage(
            id,
            (Class<MyPacket<T>>) packet.getClass(), // todo
            MyPacket::saveToData,
            packet::loadFromDataUSETHIS,
            MyPacket::handle
        );
    }

    public static <T> void registerServerToClient(SimpleChannel channel, MyPacket<T> packet, int id) {
        channels.put(packet.getIdentifier(), channel);

        channel.registerMessage(
            id,
            (Class<MyPacket<T>>) packet.getClass(), // todo
            MyPacket::saveToData,
            packet::loadFromDataUSETHIS,
            MyPacket::handle
        );

        // ClientSidePacketRegistry.INSTANCE.register(packet.getIdentifier(), packet);
    }

    public static void sendToTracking(MyPacket msg, BlockPos pos, World world) {
        channels.get(msg.getIdentifier())
            .send(PacketDistributor.TRACKING_CHUNK.with(() -> world.getChunkAt(pos)), msg);
    }

    public static void sendToTracking(MyPacket msg, Entity en) {
        if (en.level.isClientSide) {

        } else {

            if (msg == null) {
                return;
            }

            channels.get(msg.getIdentifier())
                .send(PacketDistributor.TRACKING_ENTITY.with(() -> en), msg);

        }
    }

}
