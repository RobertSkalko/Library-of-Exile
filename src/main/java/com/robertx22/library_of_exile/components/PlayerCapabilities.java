package com.robertx22.library_of_exile.components;

import com.robertx22.library_of_exile.components.forge.ICommonCap;
import com.robertx22.library_of_exile.components.forge.IPlayerCap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.HashMap;

public class PlayerCapabilities {
    static HashMap<String, Capability<? extends IPlayerCap>> caps = new HashMap<>();

    public static void register(Capability<? extends IPlayerCap> cap, IPlayerCap obj) {
        caps.put(obj.getCapIdForSyncing(), cap);
    }

    public static void saveAllOnDeath(PlayerEvent.Clone event) {
        caps.values()
            .forEach(x -> {
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

    }

    public static void syncAllToClient(PlayerEntity player) {

        try {
            caps.values()
                .forEach(x -> {
                    player.getCapability(x)
                        .orElse(null)
                        .syncToClient(player);
                });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static IPlayerCap get(PlayerEntity player, String id) {
        Capability<? extends IPlayerCap> cap = caps.get(id);
        return player.getCapability(cap)
            .orElse(null);
    }

}
