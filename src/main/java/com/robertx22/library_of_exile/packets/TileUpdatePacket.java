package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class TileUpdatePacket extends MyPacket<TileUpdatePacket> {

    public BlockPos pos;
    public CompoundNBT nbt;

    public TileUpdatePacket() {

    }

    public TileUpdatePacket(TileEntity be) {
        this.pos = be.getBlockPos();
        this.nbt = be.save(new CompoundNBT());
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "givetiledata");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        pos = tag.readBlockPos();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeBlockPos(pos);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        TileEntity tile = player.level.getBlockEntity(pos);
        tile.load(player.level.getBlockState(pos), nbt);
    }

    @Override
    public MyPacket<TileUpdatePacket> newInstance() {
        return new TileUpdatePacket();
    }
}
