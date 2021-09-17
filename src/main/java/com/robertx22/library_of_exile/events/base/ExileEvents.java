package com.robertx22.library_of_exile.events.base;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;

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
    public static ExileEventCaller<OnCheckIsDevToolsRunning> CHECK_IF_DEV_TOOLS_SHOULD_RUN = new ExileEventCaller<>();
    public static ExileEventCaller<AfterDatabaseLoaded> AFTER_DATABASE_LOADED = new ExileEventCaller<>();
    public static ExileEventCaller<OnPlayerLogin> ON_PLAYER_LOGIN = new ExileEventCaller<>();
    public static ExileEventCaller<OnChestLooted> ON_CHEST_LOOTED = new ExileEventCaller<>();
    public static ExileEventCaller<IsEntityKilledValid> IS_KILLED_ENTITY_VALID = new ExileEventCaller<>();
    public static ExileEventCaller<RegisterRegistriesEvent> REGISTER_EXILE_REGISTRIES = new ExileEventCaller<>();

    public static class OnEntityTick extends ExileEvent {
        public LivingEntity entity;

        public OnEntityTick(LivingEntity entity) {
            this.entity = entity;
        }
    }

    public static class IsEntityKilledValid extends OnMobDeath {
        public boolean isValid = true;

        public IsEntityKilledValid(LivingEntity mob, LivingEntity killer) {
            super(mob, killer);
        }
    }

    public static class OnChestLooted extends ExileEvent {
        public PlayerEntity player;
        public LootContext ctx;
        public IInventory inventory;
        public BlockPos pos;

        public OnChestLooted(PlayerEntity player, LootContext ctx, IInventory inventory, BlockPos pos) {
            this.player = player;
            this.ctx = ctx;
            this.inventory = inventory;
            this.pos = pos;
        }
    }

    public static class OnCheckIsDevToolsRunning extends ExileEvent {
        public Boolean run = false;

        public OnCheckIsDevToolsRunning() {

        }
    }

    public static class OnPlayerLogin extends ExileEvent {

        public ServerPlayerEntity player;

        public OnPlayerLogin(ServerPlayerEntity player) {
            this.player = player;
        }
    }

    public static class AfterDatabaseLoaded extends ExileEvent {

        public AfterDatabaseLoaded() {

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
