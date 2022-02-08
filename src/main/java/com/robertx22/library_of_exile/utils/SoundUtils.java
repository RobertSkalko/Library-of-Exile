package com.robertx22.library_of_exile.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SoundUtils {

    public static void playSound(Entity en, SoundEvent event) {
        playSound(en, event, 1, 1);
    }

    public static void playSound(World world, BlockPos pos, SoundEvent event) {
        playSound(world, pos, event, 1, 1);
    }

    public static void playSound(Entity en, SoundEvent event, float volume, float pitch) {
        if (!en.level.isClientSide) {
            en.level.playSound(null, en.getX(), en.getY(), en.getZ(), event, SoundCategory.PLAYERS, volume, pitch);
        } else {
            en.level.playLocalSound(en.getX(), en.getY(), en.getZ(), event, SoundCategory.PLAYERS, volume, pitch, true);
        }
    }

    public static void playSound(World world, BlockPos pos, SoundEvent event, float volume, float pitch) {
        playSound(world, pos, event, SoundCategory.PLAYERS, volume, pitch);
    }

    public static void playSound(World world, BlockPos pos, SoundEvent event, SoundCategory cat, float volume, float pitch) {
        if (!world.isClientSide) {
            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), event, cat, volume, pitch);
        } else {
            world.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), event, cat, volume, pitch, true);
        }
    }

    public static void ding(World world, BlockPos pos) {
        SoundUtils.playSound(world, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, 1, 1);
    }

}
