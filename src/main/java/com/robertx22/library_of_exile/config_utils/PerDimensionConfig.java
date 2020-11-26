package com.robertx22.library_of_exile.config_utils;

import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PerDimensionConfig {

    public BlackOrWhiteList blackOrWhiteList = BlackOrWhiteList.WHITELIST;

    @ConfigEntry.Gui.CollapsibleObject
    public List<String> dimensions = new ArrayList<>();

    public boolean isAllowedIn(DimensionType biome, World world) {
        return this.isAllowedIn(biome, world.getRegistryManager());
    }

    public boolean isAllowedIn(DimensionType type, DynamicRegistryManager manager) {

        List<DimensionType> list = dimensions.stream()
            .map(x -> manager
                .get(Registry.DIMENSION_TYPE_KEY)
                .get(new Identifier(x)))
            .collect(Collectors.toList());

        if (blackOrWhiteList == BlackOrWhiteList.BLACKLIST) {
            return !list.contains(type);
        } else {
            return list.contains(type);
        }
    }

}
