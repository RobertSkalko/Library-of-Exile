package com.robertx22.library_of_exile.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class EntityUtils {

    public static float getVanillaMaxHealth(LivingEntity entity) {

        try {

            float val = (float) entity.getAttribute(Attributes.MAX_HEALTH)
                .getBaseValue();

            return val;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void setLoc(LivingEntity entity, Vector3d p, float yaw, float pitch) {
        entity.teleportTo(p.x, p.y, p.z);
    }

    public static void setLoc(LivingEntity entity, BlockPos p) {
        entity.teleportTo(p.getX(), p.getY(), p.getZ());
    }

}
