package com.robertx22.library_of_exile.registry;

import com.google.gson.JsonObject;
import com.robertx22.library_of_exile.registry.loaders.BaseDataPackLoader;
import com.robertx22.library_of_exile.registry.serialization.ISerializable;
import net.minecraft.resource.ReloadableResourceManager;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ExileRegistryType {

    private static List<ExileRegistryType> all = new ArrayList<>();
    private static HashMap<String, ExileRegistryType> map = new HashMap<>();

    public String id;
    ISerializable ser;
    int order;
    public SyncTime syncTime;
    public String modid;

    public ExileRegistryType(String modid, String id, int order, ISerializable ser, SyncTime synctime) {
        this.id = id;
        this.modid = modid;
        this.order = order;
        this.ser = ser;
        this.syncTime = synctime;
    }

    public static ExileRegistryType get(String id) {
        return map.get(id);
    }

    public static ExileRegistryType register(ExileRegistryType type) {
        all.add(type);
        map.put(type.id, type);
        return type;
    }

    public static ExileRegistryType register(String modid, String id, int order, ISerializable ser, SyncTime synctime) {
        ExileRegistryType type = new ExileRegistryType(modid, id, order, ser, synctime);
        return register(type);
    }

    public static List<ExileRegistryType> getInRegisterOrder(SyncTime sync) {
        List<ExileRegistryType> list = all.stream()
            .filter(x -> x.syncTime == sync)
            .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;

    }

    public static List<ExileRegistryType> getAllInRegisterOrder() {
        List<ExileRegistryType> list = new ArrayList<>(all);
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;
    }

    public static void registerJsonListeners(ReloadableResourceManager manager, String modid) {
        List<ExileRegistryType> list = new ArrayList<>(all).stream()
            .filter(x -> x.modid.equals(modid))
            .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));

        list
            .forEach(x -> {
                if (x.getLoader() != null) {
                    manager.registerReloader(x.getLoader());
                }
            });

    }

    public static void init() {

    }

    public BaseDataPackLoader getLoader() {
        return new BaseDataPackLoader(this, this.id, x -> this.ser.fromJson((JsonObject) x)) {
            @Override
            public ExileDatapackGenerator getDataPackGenerator() {
                return new ExileDatapackGenerator<>(modid, getAllForSerialization(), this.id);
            }
        };
    }

    public List getAllForSerialization() {
        return Database.getRegistry(this)
            .getSerializable();
    }

    public final ISerializable getSerializer() {
        return ser;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
