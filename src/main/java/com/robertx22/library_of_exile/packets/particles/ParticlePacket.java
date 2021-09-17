package com.robertx22.library_of_exile.packets.particles;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.utils.LoadSave;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent.Context;

public class ParticlePacket extends MyPacket<ParticlePacket> {

    private ParticlePacketData data;

    static String LOC = "info";

    public ParticlePacket() {
    }

    public ParticlePacket(ParticlePacketData data) {

        this.data = data;

    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "particle");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
        data = LoadSave.Load(ParticlePacketData.class, ParticlePacketData.empty(), tag.readNbt(), LOC);

    }

    @Override
    public void saveToData(PacketBuffer tag) {
        CompoundNBT nbt = new CompoundNBT();
        LoadSave.Save(data, nbt, LOC);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(Context ctx) {
        data.type.activate(data, ctx.getSender().level);
    }

    @Override
    public MyPacket<ParticlePacket> newInstance() {
        return new ParticlePacket();
    }
}