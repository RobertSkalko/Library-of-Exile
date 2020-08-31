package com.robertx22.library_of_exile.utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class EntityUtils {

    public static float getVanillaMaxHealth(LivingEntity entity) {

        try {

            float val = (float) entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)
                .getBaseValue();

            return val;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void setLoc(LivingEntity entity, Vec3d p, float yaw, float pitch) {
        entity.requestTeleport(p.x, p.y, p.z);
    }

    public static void setLoc(LivingEntity entity, BlockPos p) {
        entity.requestTeleport(p.getX(), p.getY(), p.getZ());
    }

}
