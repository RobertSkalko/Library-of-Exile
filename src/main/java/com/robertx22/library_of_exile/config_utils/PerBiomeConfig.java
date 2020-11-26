package com.robertx22.library_of_exile.config_utils;

import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerBiomeConfig {

    public BlackOrWhiteList blackOrWhiteList = BlackOrWhiteList.BLACKLIST;

    @ConfigEntry.Gui.CollapsibleObject
    public List<String> biomes = new ArrayList<>();

    public boolean isAllowedIn(Biome biome, World world) {
        return this.isAllowedIn(biome, world.getRegistryManager());
    }

    public boolean isAllowedIn(Biome biome, DynamicRegistryManager manager) {

        List<Biome> list = biomes.stream()
            .map(x -> manager
                .get(Registry.BIOME_KEY)
                .get(new Identifier(x)))
            .collect(Collectors.toList());

        if (blackOrWhiteList == BlackOrWhiteList.BLACKLIST) {
            return !list.contains(biome);
        } else {
            return list.contains(biome);
        }

    }

}
