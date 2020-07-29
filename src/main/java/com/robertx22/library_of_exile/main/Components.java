package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.EntityInfoComponent;
import nerdhub.cardinal.components.api.ComponentRegistry;
import nerdhub.cardinal.components.api.ComponentType;
import nerdhub.cardinal.components.api.event.EntityComponentCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class Components {

    public static Components INSTANCE;

    public ComponentType<EntityInfoComponent.IEntityInfo> ENTITY_INFO =
        ComponentRegistry.INSTANCE.registerIfAbsent(
            new Identifier("library_of_exile", "entity_info"),
            EntityInfoComponent.IEntityInfo.class)
            .attach(EntityComponentCallback.event(LivingEntity.class), x -> new EntityInfoComponent.DefaultImpl(x));

}
