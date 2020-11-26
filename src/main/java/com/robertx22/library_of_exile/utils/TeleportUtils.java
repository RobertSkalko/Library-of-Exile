package com.robertx22.library_of_exile.utils;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class TeleportUtils {

    public static void teleport(ServerPlayerEntity player, BlockPos pos) {
        teleport(player, pos, player.world.getDimension());
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, DimensionType dimension) {
        teleport(player, pos, player.world.getRegistryManager()
            .getDimensionTypes()
            .getId(dimension));
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, Identifier dimension) {
        try {
            String command = "/execute in " + dimension.toString() + " run tp " + player
                .getDisplayName()
                .asString() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ();

            player
                .getServer()
                .getCommandManager()
                .execute(player
                    .getServer()
                    .getCommandSource(), command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


