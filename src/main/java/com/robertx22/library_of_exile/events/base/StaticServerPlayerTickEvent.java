package com.robertx22.library_of_exile.events.base;

import com.robertx22.library_of_exile.utils.EntityUtils;
import com.robertx22.library_of_exile.utils.TeleportUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

public class StaticServerPlayerTickEvent implements ServerTickEvents.EndTick {

    public static HashMap<UUID, PlayerTickData> PlayerTickDatas = new HashMap<UUID, PlayerTickData>();

    @Override
    public void onEndTick(MinecraftServer server) {

        for (ServerPlayerEntity player : server.getPlayerManager()
            .getPlayerList()) {

            try {

                PlayerTickData data = PlayerTickDatas.get(player.getUuid());

                if (data == null) {
                    data = new PlayerTickData();
                }

                if (data.ensureTeleportData == null) {
                    if (data.removeInvlunTicks > 0) {
                        player.setInvulnerable(false);
                    }
                } else {

                    data.tick();

                    if (data.ensureTeleportData.ticks > 3) {
                        data.removeInvlunTicks = 200;
                        data.ensureTeleportData.action.accept(data.ensureTeleportData);
                        if (data.ensureTeleportData.ticksLeft < 1) {
                            data.ensureTeleportData = null;
                        }
                    }
                }

                PlayerTickDatas.put(player.getUuid(), data);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static Consumer<EnsureTeleportData> MAKE_SURE_TELEPORT = x -> {

        if (x.player.isDead()) {
            x.cancel();
            return;
        }

        x.player.setInvulnerable(true);

        if (x.player.getBlockPos()
            .getSquaredDistance(x.whereShouldTeleport) > 1000) {

            if (x.tries > 3) {
                BlockPos spawnpos = x.player.getSpawnPointPosition();
                if (spawnpos != null) {
                    EntityUtils.setLoc(x.player, spawnpos);
                }
                x.cancel(); // pray that works at least
                return;
            } else {

                x.resetTicks();

                x.tries++;

                x.player.sendMessage(new LiteralText("There was a teleport bug but the auto correction system should have teleported you back correctly"), false);

                TeleportUtils.teleport(x.player, x.whereShouldTeleport);
            }
        }

    };

    public static void makeSureTeleport(ServerPlayerEntity player, BlockPos teleportPos, Identifier dim) {

        if (!PlayerTickDatas.containsKey(player.getUuid())) {
            PlayerTickDatas.put(player.getUuid(), new PlayerTickData());
        }
        player.setInvulnerable(true);
        PlayerTickDatas.get(player.getUuid()).ensureTeleportData =
            new EnsureTeleportData(player, MAKE_SURE_TELEPORT, 50, teleportPos, dim);
    }

    public static class PlayerTickData {
        public EnsureTeleportData ensureTeleportData;
        int removeInvlunTicks = 0;

        public void tick() {
            if (ensureTeleportData != null) {
                ensureTeleportData.ticksLeft--;
                ensureTeleportData.ticks++;
            }
        }
    }

}
