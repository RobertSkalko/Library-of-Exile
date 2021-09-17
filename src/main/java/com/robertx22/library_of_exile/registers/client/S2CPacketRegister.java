package com.robertx22.library_of_exile.registers.client;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.TileUpdatePacket;
import com.robertx22.library_of_exile.packets.particles.ParticlePacket;
import com.robertx22.library_of_exile.packets.registry.EfficientRegistryPacket;
import com.robertx22.library_of_exile.packets.registry.RegistryPacket;
import com.robertx22.library_of_exile.packets.registry.TellClientToRegisterFromPackets;

public class S2CPacketRegister {

    public static void register() {

        Packets.registerServerToClient(new EfficientRegistryPacket<>());
        Packets.registerServerToClient(new RegistryPacket());
        Packets.registerServerToClient(new TellClientToRegisterFromPackets());

        Packets.registerServerToClient(new ParticlePacket());
        Packets.registerServerToClient(new TileUpdatePacket());

        //  ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
        //     EntityPacketOnClient.onPacket(ctx, buf);
        //});

    }
}
