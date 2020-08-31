package com.robertx22.library_of_exile.registers.common;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.RequestTilePacket;

public class C2SPacketRegister {

    public static void register() {

        Packets.registerClientToServerPacket(new RequestTilePacket());

    }

}


