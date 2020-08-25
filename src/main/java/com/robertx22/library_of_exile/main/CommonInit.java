package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.OnMobDamaged;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.fabricmc.api.ModInitializer;

public class CommonInit implements ModInitializer {

    @Override
    public void onInitialize() {
        Components.INSTANCE = new Components();

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnMobDamaged());

        // ServerEntityEvents.ENTITY_LOAD.register(new OnMobSpawn());

        System.out.println("Library of Exile loaded.");
    }
}
