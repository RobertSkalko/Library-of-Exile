package com.robertx22.library_of_exile.registers.client;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.TileUpdatePacket;
import com.robertx22.library_of_exile.packets.defaults.EntityPacket;
import com.robertx22.library_of_exile.packets.defaults.EntityPacketOnClient;
import com.robertx22.library_of_exile.packets.particles.ParticlePacket;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;

public class S2CPacketRegister {

    public static void register() {

        Packets.registerServerToClient(new ParticlePacket());
        Packets.registerServerToClient(new TileUpdatePacket());

        ClientSidePacketRegistry.INSTANCE.register(EntityPacket.ID, (ctx, buf) -> {
            EntityPacketOnClient.onPacket(ctx, buf);
        });

    }
}
