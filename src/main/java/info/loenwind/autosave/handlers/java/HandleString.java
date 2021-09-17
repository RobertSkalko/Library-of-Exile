package info.loenwind.autosave.handlers.java;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import net.minecraft.nbt.CompoundNBT;

import java.lang.reflect.Type;
import java.util.Set;

public class HandleString implements IHandler<String> {

    public HandleString() {
    }

    @Override
    public Class<?> getRootType() {
        return String.class;
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, CompoundNBT nbt, Type type, String name, String object)
        throws IllegalArgumentException, IllegalAccessException {
        nbt.putString(name, object);
        return true;
    }

    @Override
    public String read(Registry registry, Set<NBTAction> phase, CompoundNBT nbt, Type type, String name,
                       String object) {
        return nbt.contains(name) ? nbt.getString(name) : object;
    }

}
