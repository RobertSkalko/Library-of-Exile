package com.robertx22.library_of_exile.packets.registry;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.RegistryPackets;
import com.robertx22.library_of_exile.registry.SyncTime;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;

public class TellClientToRegisterFromPackets extends MyPacket<TellClientToRegisterFromPackets> {

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "tell_client_to_reg_from_packets");
    }

    @Override
    public void loadFromData(PacketBuffer tag) {
    }

    @Override
    public void saveToData(PacketBuffer tag) {
    }

    @Override
    public void onReceived(NetworkEvent.Context ctx) {
        RegistryPackets.registerAll(SyncTime.ON_LOGIN);
    }

    @Override
    public MyPacket<TellClientToRegisterFromPackets> newInstance() {
        return new TellClientToRegisterFromPackets();
    }

    public TellClientToRegisterFromPackets() {

    }
}