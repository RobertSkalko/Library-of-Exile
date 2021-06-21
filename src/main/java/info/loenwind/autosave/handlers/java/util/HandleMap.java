package info.loenwind.autosave.handlers.java.util;

import info.loenwind.autosave.NBTConstants;
import info.loenwind.autosave.Registry;
import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.util.HandleGenericType;
import info.loenwind.autosave.util.NBTAction;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

@SuppressWarnings({
    "rawtypes",
    "unchecked"
})
public abstract class HandleMap<T extends Map> extends HandleGenericType<T> {

    protected HandleMap(Class<? extends T> clazz) throws NoHandlerFoundException {
        this(clazz, Registry.GLOBAL_REGISTRY, new Type[0]);
    }

    protected HandleMap(Class<? extends T> clazz, Registry registry, Type... types) throws NoHandlerFoundException {
        super(clazz, registry, types);
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name, T object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        NbtList tag = new NbtList();
        for (Entry e : (Set<Entry>) object.entrySet()) {
            NbtCompound etag = new NbtCompound();
            Object key = e.getKey();
            if (key != null) {
                storeRecursive(0, registry, phase, etag, "key", key);
            } else {
                etag.putBoolean("key" + StorableEngine.NULL_POSTFIX, true);
            }
            Object val = e.getValue();
            if (val != null) {
                storeRecursive(1, registry, phase, etag, "val", val);
            } else {
                etag.putBoolean("val" + StorableEngine.NULL_POSTFIX, true);
            }
            tag.add(etag);
        }
        nbt.put(name, tag);
        return true;
    }

    @Override
    public T read(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name,
                  T object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.contains(name)) {
            if (object == null) {
                object = createMap();
            } else {
                object.clear();
            }

            NbtList tag = nbt.getList(name, NBTConstants.TAG_COMPOUND);
            for (int i = 0; i < tag.size(); i++) {
                NbtCompound etag = tag.getCompound(i);
                Object key = etag.getBoolean("key" + StorableEngine.NULL_POSTFIX) ? null : readRecursive(0, registry, phase, etag, "key", null);
                Object val = etag.getBoolean("val" + StorableEngine.NULL_POSTFIX) ? null : readRecursive(1, registry, phase, etag, "val", null);
                object.put(key, val);
            }
        }
        return object;
    }

    abstract protected T createMap();

}
