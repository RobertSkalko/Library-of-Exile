package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class OnMobDamaged extends EventConsumer<ExileEvents.OnDamageEntity> {

    @Override
    public void accept(ExileEvents.OnDamageEntity event) {

        Entity attacker = event.source.getAttacker();

        if (attacker instanceof LivingEntity) {

            EntityInfoComponent.get(event.mob)
                .getDamageStats()
                .onDamagedBy((LivingEntity) attacker, event.damage);
        }
    }

    // call after my main mod changes damage
    @Override
    public int callOrder() {
        return 10;
    }
}
