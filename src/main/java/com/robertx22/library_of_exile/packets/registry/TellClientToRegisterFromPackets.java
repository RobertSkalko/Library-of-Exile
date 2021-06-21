package com.robertx22.library_of_exile.packets.registry;

import com.robertx22.library_of_exile.main.MyPacket;
import com.robertx22.library_of_exile.main.Ref;
import com.robertx22.library_of_exile.registry.RegistryPackets;
import com.robertx22.library_of_exile.registry.SyncTime;
import net.fabricmc.fabric.api.network.PacketContext;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class TellClientToRegisterFromPackets extends MyPacket<TellClientToRegisterFromPackets> {

    @Override
    public Identifier getIdentifier() {
        return new Identifier(Ref.MODID, "tell_client_to_reg_from_packets");
    }

    @Override
    public void loadFromData(PacketByteBuf tag) {
    }

    @Override
    public void saveToData(PacketByteBuf tag) {
    }

    @Override
    public void onReceived(PacketContext ctx) {
        RegistryPackets.registerAll(SyncTime.ON_LOGIN);
    }

    @Override
    public MyPacket<TellClientToRegisterFromPackets> newInstance() {
        return new TellClientToRegisterFromPackets();
    }

    public TellClientToRegisterFromPackets() {

    }
}