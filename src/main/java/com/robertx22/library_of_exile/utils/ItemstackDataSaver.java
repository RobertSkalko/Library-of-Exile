package com.robertx22.library_of_exile.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import java.util.function.Supplier;

public class ItemstackDataSaver<T> {

    String id;
    Class<T> clazz;
    Supplier<T> constructor;

    public ItemstackDataSaver(String id, Class<T> clazz, Supplier<T> constructor) {
        this.id = id;
        this.clazz = clazz;
        this.constructor = constructor;

    }

    public boolean has(ItemStack stack) {
        return stack != null && stack.hasTag() && stack.getTag()
            .contains(id);
    }

    public T loadFrom(ItemStack stack) {

        if (stack == null) {
            return null;
        }
        if (!stack.hasTag()) {
            return null;
        }

        T object = LoadSave.Load(clazz, constructor.get(), stack.getTag(), id);

        return object;

    }

    public void saveTo(ItemStack stack, T object) {
        if (stack == null) {
            return;
        }
        if (!stack.hasTag()) {
            stack.setTag(new CompoundNBT());
        }
        if (object != null) {
            LoadSave.Save(object, stack.getTag(), id);
        }

    }
}
