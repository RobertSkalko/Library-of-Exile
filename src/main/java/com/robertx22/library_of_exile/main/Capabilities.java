package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.EntityInfoComponent;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class Capabilities {

    public static void reg() {

        CapabilityManager.INSTANCE.register(
            EntityInfoComponent.IEntityInfo.class,
            new EntityInfoComponent.Storage(),
            () -> new EntityInfoComponent.DefaultImpl(null)
        );

    }

}
