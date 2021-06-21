package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class TileUpdatePacket extends MyPacket<TileUpdatePacket> {

    public BlockPos pos;
    public NbtCompound nbt;

    public TileUpdatePacket() {

    }

    public TileUpdatePacket(BlockEntity be) {
        this.pos = be.getPos();
        this.nbt = be.writeNbt(new NbtCompound());
    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "givetiledata");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
        pos = tag.readBlockPos();
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(PacketByteBuf tag) {
        tag.writeBlockPos(pos);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {
        PlayerEntity player = ctx.getPlayer();
        BlockEntity tile = player.world.getBlockEntity(pos);
        tile.fromTag(player.world.getBlockState(pos), nbt);
    }

    @Override
    public MyPacket<TileUpdatePacket> newInstance() {
        return new TileUpdatePacket();
    }
}
