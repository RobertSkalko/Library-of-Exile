package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.registers.client.S2CPacketRegister;
import net.fabricmc.api.ClientModInitializer;

public class ClientInit implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        S2CPacketRegister.register();

    }
}
