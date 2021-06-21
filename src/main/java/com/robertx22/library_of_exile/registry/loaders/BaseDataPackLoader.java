package com.robertx22.library_of_exile.registry.loaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.robertx22.library_of_exile.main.LibraryOfExile;
import com.robertx22.library_of_exile.registry.*;
import com.robertx22.library_of_exile.utils.Watch;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public abstract class BaseDataPackLoader<T extends ExileRegistry> extends JsonDataLoader {
    private static final Gson GSON = new GsonBuilder().create();

    public String id;
    Function<JsonObject, T> serializer;
    public ExileRegistryType registryType;

    public BaseDataPackLoader(ExileRegistryType registryType, String id, Function<JsonObject, T> serializer) {
        super(GSON, id);
        Objects.requireNonNull(registryType);
        this.id = id;
        this.serializer = serializer;
        this.registryType = registryType;
    }

    public abstract ExileDatapackGenerator getDataPackGenerator();

    @Override
    protected Map<Identifier, JsonElement> prepare(ResourceManager resourceManager, Profiler profiler) {

        if (LibraryOfExile.runDevTools()) {
            this.getDataPackGenerator()
                .run(); // first generate, then load. so no errors in dev enviroment
        }

        return super.prepare(resourceManager, profiler);
    }

    static String ENABLED = "enabled";

    @Override
    protected void apply(Map<Identifier, JsonElement> mapToLoad, ResourceManager manager, Profiler profilerIn) {

        try {
            ExileRegistryContainer reg = Database.getRegistry(registryType);

            Watch normal = new Watch();
            normal.min = 50000;
            reg.unregisterAllEntriesFromDatapacks();

            mapToLoad.forEach((key, value) -> {
                try {
                    JsonObject json = value
                        .getAsJsonObject();

                    if (!json.has(ENABLED) || json.get(ENABLED)
                        .getAsBoolean()) {
                        T object = serializer.apply(json);
                        object.registerToExileRegistry();
                    }
                } catch (Exception exception) {
                    System.out.println(id + " is a broken datapack entry.");
                    exception.printStackTrace();
                }
            });

            normal.print("Loading " + registryType.id + " jsons ");

            if (reg
                .isEmpty()) {
                throw new RuntimeException("Exile Registry of type " + registryType.id + " is EMPTY after datapack loading!");
            } else {
                // System.out.println(registryType.name() + " Registry succeeded loading: " + reg.getSize() + " datapack entries.");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

}