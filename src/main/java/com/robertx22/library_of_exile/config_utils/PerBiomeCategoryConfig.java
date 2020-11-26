package com.robertx22.library_of_exile.config_utils;

import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerBiomeCategoryConfig {

    public BlackOrWhiteList blackOrWhiteList = BlackOrWhiteList.BLACKLIST;

    @ConfigEntry.Gui.CollapsibleObject
    public List<String> categories = new ArrayList<>();

    public boolean isAllowedIn(Biome.Category biome, World world) {
        return this.isAllowedIn(biome, world.getRegistryManager());
    }

    public static PerBiomeCategoryConfig ofDefaultGroundStructure() {
        PerBiomeCategoryConfig c = new PerBiomeCategoryConfig();
        c.categories.add(Biome.Category.OCEAN.getName());
        c.categories.add(Biome.Category.NONE.getName());
        c.categories.add(Biome.Category.RIVER.getName());
        return c;
    }

    public boolean isAllowedIn(Biome.Category category, DynamicRegistryManager manager) {
        List<Biome.Category> list = categories.stream()
            .map(x -> Biome.Category.byName(x))
            .collect(Collectors.toList());

        if (blackOrWhiteList == BlackOrWhiteList.BLACKLIST) {
            return !list.contains(category);
        } else {
            return list.contains(category);
        }
    }

}

