package com.robertx22.library_of_exile.components.forge;

import net.minecraft.nbt.CompoundNBT;

public interface ICommonCap {
    CompoundNBT saveToNBT();

    void loadFromNBT(CompoundNBT value);

}