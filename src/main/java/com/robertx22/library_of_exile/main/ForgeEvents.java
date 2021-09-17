package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.forge.ICommonCap;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ForgeEvents {

    public static List<Capability<? extends ICommonCap>> SAVED_ON_DEATH_CAPABILITIES = new ArrayList<>();

    public static <T extends Event> void registerForgeEvent(Class<T> clazz, Consumer<T> event) {
        if (clazz.isAssignableFrom(IModBusEvent.class)) { // todo
            FMLJavaModLoadingContext.get()
                .getModEventBus()
                .addListener(event);
        } else {
            MinecraftForge.EVENT_BUS.addListener(event);
        }
    }

    public static void register() {

        registerForgeEvent(LivingDeathEvent.class, event -> {
            if (event.getEntityLiving() instanceof PlayerEntity == false && event.getSource()
                .getEntity() instanceof LivingEntity) {
                ExileEvents.MOB_DEATH.callEvents(new ExileEvents.OnMobDeath(event.getEntityLiving(), (LivingEntity) event.getSource()
                    .getEntity()));
            }
        });

        registerForgeEvent(PlayerEvent.Clone.class, event -> {

            SAVED_ON_DEATH_CAPABILITIES.forEach(x -> {
                PlayerEntity original = event.getOriginal();
                PlayerEntity current = event.getPlayer();

                try {
                    ICommonCap data = current.getCapability(x)
                        .orElse(null);
                    data.loadFromNBT(original.getCapability(x)
                        .orElse(null)
                        .saveToNBT());
                    // todo add sync
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

        });

        registerForgeEvent(FMLServerStartedEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });

        registerForgeEvent(AddReloadListenerEvent.class, event -> {
            CommonInit.onDatapacksReloaded();
        });
    }
}