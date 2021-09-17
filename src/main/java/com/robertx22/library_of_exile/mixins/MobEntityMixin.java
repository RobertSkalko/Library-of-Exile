package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.components.EntityInfoComponent;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Inject(method = "finalizeSpawn(Lnet/minecraft/world/IServerWorld;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/ILivingEntityData;Lnet/minecraft/nbt/CompoundNBT;)Lnet/minecraft/entity/ILivingEntityData;", at = @At("HEAD"))
    private void hook(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason, ILivingEntityData entityData, CompoundNBT entityTag, CallbackInfoReturnable<ILivingEntityData> ci) {
        try {
            LivingEntity en = (LivingEntity) (Object) this;
            EntityInfoComponent.IEntityInfo info = EntityInfoComponent.get(en);
            info.setSpawnReasonOnCreate(spawnReason);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
