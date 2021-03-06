package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "onKilledBy", at = @At("HEAD"))
    public void hookOnDeath(LivingEntity adversary, CallbackInfo ci) {
        try {
            LivingEntity victim = (LivingEntity) (Object) this;
            ExileEvents.MOB_DEATH.callEvents(new ExileEvents.OnMobDeath(victim, adversary));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "tick()V", at = @At("HEAD"))
    public void hookOnTick(CallbackInfo ci) {
        try {
            LivingEntity entity = (LivingEntity) (Object) this;
            ExileEvents.LIVING_ENTITY_TICK.callEvents(new ExileEvents.OnEntityTick(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), argsOnly = true)
    public float hookOnDamage(float amount, DamageSource source) {
        try {
            LivingEntity entity = (LivingEntity) (Object) this;
            return ExileEvents.DAMAGE_BEFORE_CALC.callEvents(new ExileEvents.OnDamageEntity(source, amount, entity)).damage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    @ModifyVariable(method = "damage", at = @At("TAIL"), argsOnly = true)
    public float hookAfterDamage(float amount, DamageSource source) {
        try {
            LivingEntity entity = (LivingEntity) (Object) this;
            return ExileEvents.DAMAGE_AFTER_CALC.callEvents(new ExileEvents.OnDamageEntity(source, amount, entity)).damage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return amount;
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void hookOnTick(DamageSource source, float amount, CallbackInfo ci) {
        try {
            LivingEntity entity = (LivingEntity) (Object) this;
            ExileEvents.DAMAGE_BEFORE_APPLIED.callEvents(new ExileEvents.OnDamageEntity(source, amount, entity));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
