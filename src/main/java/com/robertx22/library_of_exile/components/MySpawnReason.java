package com.robertx22.library_of_exile.components;

import net.minecraft.entity.SpawnReason;

public enum MySpawnReason {
    NATURAL,
    SPAWNER,
    OTHER;

    public static MySpawnReason get(SpawnReason reason) {

        if (reason == SpawnReason.SPAWNER) {
            return SPAWNER;
        }
        if (reason == SpawnReason.NATURAL || reason == SpawnReason.CHUNK_GENERATION || reason == SpawnReason.STRUCTURE) {
            return NATURAL;
        }

        return OTHER;
    }

}
