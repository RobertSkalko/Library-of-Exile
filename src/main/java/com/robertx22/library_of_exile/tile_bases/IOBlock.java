package com.robertx22.library_of_exile.tile_bases;

import net.minecraft.item.ItemStack;

import java.util.List;

public interface IOBlock {

    boolean isItemValidInput(ItemStack stack);

    boolean isAutomatable();

    boolean isOutputSlot(int slot);

    List<Integer> inputSlots();

}
