package com.robertx22.library_of_exile.events.base;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.function.Consumer;

public class EnsureTeleportData {

    ServerPlayerEntity player;
    Consumer<EnsureTeleportData> action;
    int ticksLeft;
    int ticks = 0;
    BlockPos whereShouldTeleport;
    Identifier dimensionToTpTo;
    int tries = 0;

    int origTicksLeft;

    public EnsureTeleportData(ServerPlayerEntity player, Consumer<EnsureTeleportData> action, int ticksLeft, BlockPos whereShouldTeleport, Identifier dimensionToTpTo) {
        this.player = player;
        this.action = action;
        this.ticksLeft = ticksLeft;
        this.whereShouldTeleport = whereShouldTeleport;
        this.dimensionToTpTo = dimensionToTpTo;
        this.origTicksLeft = ticksLeft;
    }

    public void cancel() {
        this.ticksLeft = -1;
    }

    public void resetTicks() {
        this.ticks = 0;
        this.ticksLeft = origTicksLeft;
    }
}