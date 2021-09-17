package com.robertx22.library_of_exile.components.forge;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;

public abstract class BaseProvider<TYPE, OWNER_CLASS> implements ICapabilitySerializable<CompoundNBT> {

    public OWNER_CLASS owner;

    public BaseProvider(OWNER_CLASS owner) {
        this.owner = owner;

        impl = newDefaultImpl(owner);
        cap = LazyOptional.of(() -> impl);
    }

    public abstract TYPE newDefaultImpl(OWNER_CLASS owner);

    public abstract Capability<TYPE> dataInstance();

    TYPE impl;
    private final LazyOptional<TYPE> cap;

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) dataInstance().getStorage()
            .writeNBT(dataInstance(), impl, null);

    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        dataInstance().getStorage()
            .readNBT(dataInstance(), impl, null, nbt);

    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (cap == dataInstance()) {
            return this.cap.cast();
        }
        return LazyOptional.empty();
    }
}