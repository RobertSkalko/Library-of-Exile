package com.robertx22.library_of_exile.utils;

import com.robertx22.library_of_exile.events.base.StaticServerPlayerTickEvent;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.server.ServerWorld;

public class TeleportUtils {

    public static void teleport(ServerPlayerEntity player, BlockPos pos) {
        teleport(player, pos, player.level.dimensionType());
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, DimensionType dimension) {
        teleport(player, pos, player.level.registryAccess()
            .dimensionTypes()
            .getKey(dimension));
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, ResourceLocation dimension) {
        teleport(player, pos, dimension, true);
    }

    public static void teleport(ServerPlayerEntity player, BlockPos pos, ResourceLocation dimension, Boolean addSafety) {
        try {

            ServerWorld world = player.getServer()
                .getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, dimension));

            if (world == null) {
                System.out.println("No world with id: " + dimension);
                return;
            }

            player.teleportTo(world, pos.getX(), pos.getY(), pos.getZ(), 0, 0);

            if (addSafety) {
                StaticServerPlayerTickEvent.makeSureTeleport(player, pos, dimension);
            }


            /*
            ServerCommandSource source = new ServerCommandSource(player, player.getPos(), player.getRotationClient(),
                player.world instanceof ServerWorld ? (ServerWorld) player.world : null, 5, player.getName()
                .getString(), player.getDisplayName(), player.world.getServer(), player).withSilent();

            String command = "/execute in " + dimension.toString() + " run tp " + "@p" +
                " " + pos.getX() + " " + pos.getY() + " " + pos.getZ();

            player
                .getServer()
                .getCommandManager()
                .execute(source, command);


             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


