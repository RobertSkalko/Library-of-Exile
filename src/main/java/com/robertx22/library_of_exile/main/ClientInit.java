package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.registers.client.S2CPacketRegister;

public class ClientInit {

    public static void onInitializeClient() {
        S2CPacketRegister.register();

    }
}
