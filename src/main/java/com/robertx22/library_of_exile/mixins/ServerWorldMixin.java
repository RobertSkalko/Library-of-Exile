package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.main.Components;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//copied from api cus i dont want to add it just cus of this one mixin
@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Shadow
    private boolean inEntityTick;

    // Call our load event after vanilla has loaded the entity
    @Inject(method = "loadEntityUnchecked", at = @At("TAIL"))
    private void onLoadEntity(Entity entity, CallbackInfo ci) {
        try {
            if (!this.inEntityTick) { // Copy vanilla logic, we cannot load entities while the game is ticking entities
                if (entity instanceof LivingEntity) {
                    Components.INSTANCE.ENTITY_INFO.get(entity)
                        .spawnInit();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
