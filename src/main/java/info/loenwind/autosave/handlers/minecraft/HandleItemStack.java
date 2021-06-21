package info.loenwind.autosave.handlers.minecraft;

import java.lang.reflect.Type;
import java.util.Set;



import info.loenwind.autosave.Registry;
import info.loenwind.autosave.engine.StorableEngine;
import info.loenwind.autosave.exceptions.NoHandlerFoundException;
import info.loenwind.autosave.handlers.IHandler;
import info.loenwind.autosave.util.NBTAction;
import info.loenwind.autosave.util.VersionProxy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class HandleItemStack implements IHandler<ItemStack> {

  public HandleItemStack() {
  }

  @Override
  public Class<?> getRootType() {
    return ItemStack.class;
  }

  @Override
  public boolean store(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name, ItemStack object)
      throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (object.isEmpty()) {
      nbt.putBoolean(name + StorableEngine.EMPTY_POSTFIX, true);
    } else {
      NbtCompound tag = new NbtCompound();
      object.writeNbt(tag);
      nbt.put(name, tag);
    }
    return true;
  }

  @Override
  public ItemStack read(Registry registry, Set<NBTAction> phase, NbtCompound nbt, Type type, String name,
      ItemStack object) throws IllegalArgumentException, IllegalAccessException, InstantiationException, NoHandlerFoundException {
    if (nbt.contains(name)) {
      NbtCompound tag = nbt.getCompound(name);
      return ItemStack.fromNbt(tag);
    } else if (nbt.contains(name + StorableEngine.EMPTY_POSTFIX)) {
      return ItemStack.EMPTY;
    }
    return object;
  }
}
