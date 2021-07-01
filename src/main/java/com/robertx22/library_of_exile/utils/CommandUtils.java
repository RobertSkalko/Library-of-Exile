package com.robertx22.library_of_exile.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

public class CommandUtils {

    public static void execute(PlayerEntity player, String command) {

        ServerCommandSource source = getCommandSource(player);

        player
            .getServer()
            .getCommandManager()
            .execute(source, command);
    }

    public static ServerCommandSource getCommandSource(Entity entity) {
        return new ServerCommandSource(
            // this doesnt send messages to spam server
            CommandOutput.DUMMY,
            entity.getPos(),
            entity.getRotationClient(),
            entity.world instanceof ServerWorld ? (ServerWorld) entity.world : null,
            4,
            entity.getName()
                .getString(),
            entity.getDisplayName(),
            entity.world.getServer(),
            entity);
    }
}
