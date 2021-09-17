package info.loenwind.autosave.handlers.minecraft;

import info.loenwind.autosave.Registry;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;
import java.util.Set;

public class HandleBlockPos implements IHandler<BlockPos> {

    public HandleBlockPos() {
    }

    @Override
    public Class<?> getRootType() {
        return BlockPos.class;
    }

    @Override
    public boolean store(Registry registry, Set<NBTAction> phase, CompoundNBT nbt, Type type, String name, BlockPos object)
        throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        nbt.putLong(name, object.asLong());
        return true;
    }

    @Override
    public BlockPos read(Registry registry, Set<NBTAction> phase, CompoundNBT nbt, Type type, String name,
                         BlockPos object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
        if (nbt.contains(name)) {
            return BlockPos.of(nbt.getLong(name));
        }
        return object;
    }
}
