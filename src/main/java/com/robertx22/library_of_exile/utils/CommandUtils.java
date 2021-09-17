package com.robertx22.library_of_exile.utils;

import net.minecraft.command.CommandSource;
import net.minecraft.command.ICommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;

public class CommandUtils {

    public static void execute(PlayerEntity player, String command) {

        CommandSource source = getCommandSource(player);

        player
            .getServer()
            .getCommands()
            .performCommand(source, command);
    }

    public static CommandSource getCommandSource(Entity entity) {
        return new CommandSource(
            // this doesnt send messages to spam server
            ICommandSource.NULL,
            entity.position(),
            entity.getRotationVector(),
            entity.level instanceof ServerWorld ? (ServerWorld) entity.level : null,
            4,
            entity.getName()
                .getString(),
            entity.getDisplayName(),
            entity.level.getServer(),
            entity);
    }
}
