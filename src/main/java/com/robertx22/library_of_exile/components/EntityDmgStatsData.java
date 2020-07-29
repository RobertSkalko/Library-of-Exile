package com.robertx22.library_of_exile.components;

import info.loenwind.autosave.annotations.Storable;
import info.loenwind.autosave.annotations.Store;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Storable
public class EntityDmgStatsData {

    @Store
    private HashMap<String, Float> map = new HashMap<>();

    public void onDamagedBy(LivingEntity entity, float dmg) {

        if (entity == null) {
            return;
        } else {
            if (entity instanceof PlayerEntity) {
                String id = entity.getUuid()
                    .toString();
                map.put(id, dmg + map.getOrDefault(id, 0F));
            }
        }
    }

    public LivingEntity getHighestDamager(ServerWorld world) {

        Optional<Map.Entry<String, Float>> opt = map.entrySet()
            .stream()
            .max((one, two) -> one.getValue() >= two.getValue() ? 1 : -1);

        if (opt.isPresent()) {

            String id = opt.get()
                .getKey();

            Entity en = world.getEntity(UUID.fromString(id));

            if (en instanceof LivingEntity) {
                return (LivingEntity) en;
            }
        }
        return null;
    }

    public float getTotalPlayerDamage() {
        if (map.isEmpty()) {
            return 0;
        }
        return map.values()
            .stream()
            .reduce((x, y) -> x + y)
            .get();
    }

}
