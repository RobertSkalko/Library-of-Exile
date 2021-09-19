package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.PlayerCapabilities;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Consumer;

public class ForgeEvents {

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event, EventPriority priority) {
        if (clazz.isAssignableFrom(IModBusEvent.class)) { // todo
            FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(priority, event);
        } else {
            MinecraftForge.EVENT_BUS.addListener(priority, event);
        }
    }

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        registerForgeEvent(clazz, event, EventPriority.NORMAL);
    }

    public static void register() {

        registerForgeEvent(LivingDamageEvent.class, event -> {
            ExileEvents.OnDamageEntity after = ExileEvents.DAMAGE_BEFORE_CALC.callEvents(
                new ExileEvents.OnDamageEntity(event.getSource(), event.getAmount(), event.getEntityLiving())
            );
            event.setAmount(after.damage);
        }, EventPriority.HIGHEST);

        registerForgeEvent(LivingDamageEvent.class, event -> {
            ExileEvents.OnDamageEntity after = ExileEvents.DAMAGE_AFTER_CALC.callEvents(
                new ExileEvents.OnDamageEntity(event.getSource(), event.getAmount(), event.getEntityLiving())
            );
            event.setAmount(after.damage);
        }, EventPriority.LOWEST);

        registerForgeEvent(LivingEvent.LivingUpdateEvent.class, event -> {
            LivingEntity entity = event.getEntityLiving();
            ExileEvents.LIVING_ENTITY_TICK.callEvents(new ExileEvents.OnEntityTick(entity));
        });

        registerForgeEvent(LivingDeathEvent.class, event -> {
            if (event.getEntityLiving() instanceof PlayerEntity == false && event.getSource()
                .getEntity() instanceof LivingEntity) {
                ExileEvents.MOB_DEATH.callEvents(new ExileEvents.OnMobDeath(event.getEntityLiving(), (LivingEntity) event.getSource()
                    .getEntity()));
            }
        });

        registerForgeEvent(PlayerEvent.Clone.class, event -> {
            PlayerCapabilities.saveAllOnDeath(event);
            PlayerCapabilities.syncAllToClient(event.getPlayer());
        });

        registerForgeEvent(FMLServerStartedEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });

        registerForgeEvent(AddReloadListenerEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });
    }
}