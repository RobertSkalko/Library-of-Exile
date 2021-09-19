package com.robertx22.library_of_exile.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class LibClientOnly {
    public static PlayerEntity getPlayer() {
        return Minecraft.getInstance().player;
    }
}
