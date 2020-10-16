package com.robertx22.library_of_exile.events.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;

public class ExileEvents {

    public static ExileEventCaller<OnEntityTick> LIVING_ENTITY_TICK = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobExpDrop> MOB_EXP_DROP = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobDeath> MOB_DEATH = new ExileEventCaller<>();
    public static ExileEventCaller<OnPlayerDeath> PLAYER_DEATH = new ExileEventCaller<>();
    public static ExileEventCaller<OnMobKilledByPlayer> MOB_KILLED_BY_PLAYER = new ExileEventCaller<>();
    public static ExileEventCaller<OnSetupLootChance> SETUP_LOOT_CHANCE = new ExileEventCaller<>();

    public static ExileEventCaller<OnDamageEntity> DAMAGE_BEFORE_CALC = new ExileEventCaller<>();
    public static ExileEventCaller<OnDamageEntity> DAMAGE_AFTER_CALC = new ExileEventCaller<>();
    public static ExileEventCaller<OnDamageEntity> DAMAGE_BEFORE_APPLIED = new ExileEventCaller<>();

    public static class OnEntityTick extends ExileEvent {
        public LivingEntity entity;

        public OnEntityTick(LivingEntity entity) {
            this.entity = entity;
        }
    }

    public static class OnMobExpDrop extends ExileEvent {
        public LivingEntity mobKilled;
        public float exp;

        public OnMobExpDrop(LivingEntity mobKilled, float exp) {
            this.mobKilled = mobKilled;
            this.exp = exp;
        }
    }

    public static class OnSetupLootChance extends ExileEvent {
        public LivingEntity mobKilled;
        public PlayerEntity player;
        public float lootChance;

        public OnSetupLootChance(LivingEntity mobKilled, PlayerEntity player, float lootChance) {
            this.mobKilled = mobKilled;
            this.player = player;
            this.lootChance = lootChance;
        }
    }

    public static class OnMobDeath extends ExileEvent {
        public LivingEntity mob;
        public LivingEntity killer;

        public OnMobDeath(LivingEntity mob, LivingEntity killer) {
            this.mob = mob;
            this.killer = killer;
        }
    }

    public static class OnPlayerDeath extends ExileEvent {
        public PlayerEntity player;
        public LivingEntity killer;

        public OnPlayerDeath(PlayerEntity player) {
            this.player = player;
        }
    }

    public static class OnMobKilledByPlayer extends ExileEvent {
        public LivingEntity mob;
        public PlayerEntity player;

        public OnMobKilledByPlayer(LivingEntity mob, PlayerEntity player) {
            this.mob = mob;
            this.player = player;
        }
    }

    public static class OnDamageEntity extends ExileEvent {
        public DamageSource source;
        public float damage;
        public LivingEntity mob;

        public OnDamageEntity(DamageSource source, float damage, LivingEntity mob) {
            this.source = source;
            this.damage = damage;
            this.mob = mob;
        }
    }

}
