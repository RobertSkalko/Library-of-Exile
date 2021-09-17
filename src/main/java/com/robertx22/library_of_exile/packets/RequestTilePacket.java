package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.main.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class RequestTilePacket extends MyPacket<RequestTilePacket> {

    public BlockPos pos;

    public RequestTilePacket() {

    }

    public RequestTilePacket(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "reqtiledata");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        pos = tag.readBlockPos();

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeBlockPos(pos);

    }

    @Override
    public void onReceived(Context ctx) {
        PlayerEntity player = ctx.getSender();
        TileEntity tile = player.level.getBlockEntity(pos);
        Packets.sendToClient(player, new TileUpdatePacket(tile));
    }

    @Override
    public MyPacket<RequestTilePacket> newInstance() {
        return new RequestTilePacket();
    }
}
