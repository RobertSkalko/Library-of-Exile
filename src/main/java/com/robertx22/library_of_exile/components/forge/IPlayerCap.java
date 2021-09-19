package com.robertx22.library_of_exile.components.forge;

import com.robertx22.library_of_exile.main.Packets;
import com.robertx22.library_of_exile.packets.SyncPlayerCapToClient;
import net.minecraft.entity.player.PlayerEntity;

public interface IPlayerCap extends ICommonCap {

    String getCapIdForSyncing();

    public default void syncToClient(PlayerEntity player) {
        Packets.sendToClient(player, new SyncPlayerCapToClient(player, getCapIdForSyncing()));
    }
}
