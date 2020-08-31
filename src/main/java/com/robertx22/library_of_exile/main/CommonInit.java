package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.OnMobDamaged;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.registers.common.C2SPacketRegister;
import net.fabricmc.api.ModInitializer;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {

        Components.INSTANCE = new Components();

        C2SPacketRegister.register();

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnMobDamaged());

        System.out.println("Library of Exile loaded.");
    }

}
