package info.loenwind.autosave;

import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.NullHelper;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.nbt.NbtCompound;

/**
 * Restore an object's fields from NBT data.
 */
public class Reader {

    public static <T> void read(Registry registry, Set<NBTAction> phase, NbtCompound tag, T object) {
        try {
            StorableEngine.read(registry, phase, tag, object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (NoHandlerFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> void read(Set<NBTAction> phase, NbtCompound tag, T object) {
        read(Registry.GLOBAL_REGISTRY, NullHelper.notnull(phase, "Missing phase"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void read(Registry registry, NBTAction phase, NbtCompound tag, T object) {
        read(registry, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void read(NBTAction phase, NbtCompound tag, T object) {
        read(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.of(phase), "EnumSet.of()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void read(Registry registry, NbtCompound tag, T object) {
        read(registry, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> void read(NbtCompound tag, T object) {
        read(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"), NullHelper.notnull(tag, "Missing NBT"), object);
    }

    public static <T> T readField(NbtCompound tag, Class<T> fieldClass, String fieldName, T object) {
        try {
            return StorableEngine.getSingleField(Registry.GLOBAL_REGISTRY, NullHelper.notnullJ(EnumSet.allOf(NBTAction.class), "EnumSet.allOf()"),
                NullHelper.notnull(tag, "Missing NBT"), NullHelper.notnull(fieldName, "Missing field name"), NullHelper.notnull(fieldClass, "Missing field class"),
                object);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (NoHandlerFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
