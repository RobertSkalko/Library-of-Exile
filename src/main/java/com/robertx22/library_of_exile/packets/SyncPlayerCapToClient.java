package com.robertx22.library_of_exile.packets;

import com.robertx22.library_of_exile.components.PlayerCapabilities;
import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;

public class SyncPlayerCapToClient extends MyPacket<SyncPlayerCapToClient> {

    public String capid;
    public CompoundNBT nbt;

    public SyncPlayerCapToClient() {

    }

    public SyncPlayerCapToClient(PlayerEntity player, String capid) {
        this.nbt = PlayerCapabilities.get(player, capid)
            .saveToNBT();
        this.capid = capid;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "syncplayercap");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        capid = tag.readUtf(100);
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        tag.writeUtf(capid, 100);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(ExilePacketContext ctx) {

        try {
            PlayerEntity player = ctx.getPlayer();

            if (player.level.isClientSide) { // just an extra check
                PlayerCapabilities.get(player, capid)
                    .loadFromNBT(nbt);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyPacket<SyncPlayerCapToClient> newInstance() {
        return new SyncPlayerCapToClient();
    }
}

