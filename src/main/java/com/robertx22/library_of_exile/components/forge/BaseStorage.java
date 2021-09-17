package com.robertx22.library_of_exile.components.forge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public interface BaseStorage<TYPE extends ICommonCap> extends Capability.IStorage<TYPE> {

    @Override
    public default INBT writeNBT(Capability<TYPE> capability, TYPE instance, Direction side) {
        return instance.saveToNBT();
    }

    @Override
    public default void readNBT(Capability<TYPE> capability, TYPE instance, Direction side, INBT nbt) {
        instance.loadFromNBT((CompoundNBT) nbt);
    }

}