package com.robertx22.library_of_exile.packets.defaults;

import com.robertx22.library_of_exile.main.Ref;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.core.Registry;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.protocol.Packet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class EntityPacket {

    public static final ResourceLocation ID = new ResourceLocation(Ref.MODID, "spawn_entity");

    public static Packet<?> createPacket(Entity entity) {
        PacketBuffer buf = createBuffer();
        buf.writeVarInt(Registry.ENTITY_TYPE.getId(entity.getType()));
        buf.writeUUID(entity.getUUID());
        buf.writeVarInt(entity.getId());
        buf.writeDouble(entity.getX());
        buf.writeDouble(entity.getY());
        buf.writeDouble(entity.getZ());
        buf.writeByte(MathHelper.floor(entity.xRot * 256.0F / 360.0F));
        buf.writeByte(MathHelper.floor(entity.yRot * 256.0F / 360.0F));
        buf.writeFloat(entity.xRot);
        buf.writeFloat(entity.yRot);
        return ServerSidePacketRegistry.INSTANCE.toPacket(ID, buf);
    }

    private static PacketBuffer createBuffer() {
        return new PacketBuffer(Unpooled.buffer());
    }

}