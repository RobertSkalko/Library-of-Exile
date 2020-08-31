package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.main.Ref;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class RequestTilePacket extends MyPacket<RequestTilePacket> {

    public BlockPos pos;

    public RequestTilePacket() {

    }

    public RequestTilePacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "reqtiledata");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        BlockEntity tile = player.world.getBlockEntity(pos);
        Packets.sendToClient(player, new TileUpdatePacket(tile));
    }

    @Override
    public MyPacket<RequestTilePacket> newInstance() {
        return new RequestTilePacket();
    }
}
