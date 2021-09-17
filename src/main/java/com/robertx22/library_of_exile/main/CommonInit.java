package com.robertx22.library_of_exile.main;

import com.robertx22.library_of_exile.components.OnMobDamaged;
import com.robertx22.library_of_exile.events.base.EventConsumer;
import com.robertx22.library_of_exile.events.base.ExileEvents;
import com.robertx22.library_of_exile.packets.registry.TellClientToRegisterFromPackets;
import com.robertx22.library_of_exile.registers.common.C2SPacketRegister;
import com.robertx22.library_of_exile.registry.Database;
import com.robertx22.library_of_exile.registry.SyncTime;
import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.player.ServerPlayerEntity;

public class CommonInit implements ModInitializer {

    public static void onDatapacksReloaded() {
        try {

            Database.backup();
            Database.checkGuidValidity();
            Database.unregisterInvalidEntries();
            Database.getAllRegistries()
                .forEach(x -> x.onAllDatapacksLoaded());
            ExileEvents.AFTER_DATABASE_LOADED.callEvents(new ExileEvents.AfterDatabaseLoaded());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onInitialize() {

        Components.reg();

        ForgeEvents.register();

        /*
        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register(new ServerLifecycleEvents.EndDataPackReload() {
            @Override
            public void endDataPackReload(MinecraftServer server, DataPackRegistries serverResourceManager, boolean success) {
                onDatapacksReloaded();
            }


        });

*/

        ExileEvents.ON_PLAYER_LOGIN.register(new EventConsumer<ExileEvents.OnPlayerLogin>() {
            @Override
            public void accept(ExileEvents.OnPlayerLogin event) {
                ServerPlayerEntity player = event.player;

                Database.sendPacketsToClient(player, SyncTime.ON_LOGIN);
                Packets.sendToClient(player, new TellClientToRegisterFromPackets());
                Database.restoreFromBackupifEmpty();
            }
        });

        C2SPacketRegister.register();

        ExileEvents.DAMAGE_AFTER_CALC.register(new OnMobDamaged());

        System.out.println("Library of Exile loaded.");
    }

}
