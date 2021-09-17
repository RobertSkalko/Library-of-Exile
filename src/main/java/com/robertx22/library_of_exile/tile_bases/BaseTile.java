package com.robertx22.library_of_exile.tile_bases;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class BaseTile extends TileEntity implements IOBlock, ISidedInventory, ITickableTileEntity, INamedContainerProvider {

    public BaseTile(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    protected ItemStack[] itemStacks;

    public int ticks = 0;
    public short cookTime = 0;
    public int fuel = 0;

    public abstract int ticksRequired();

    public abstract void finishCooking();

    public abstract boolean isCooking();

    public abstract int tickRate();

    public abstract void doActionEveryTime();

    public abstract int getCookTime();

    @Override
    public void tick() {
        try {
            if (!this.level.isClientSide) {

                ticks++;
                if (ticks > tickRate()) {
                    ticks = 0;

                    doActionEveryTime();

                    if (isCooking()) {

                        cookTime += tickRate();

                        if (cookTime >= ticksRequired()) {
                            finishCooking();
                            cookTime = 0;
                        }

                    } else {
                        cookTime = 0;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static final short COOK_TIME_FOR_COMPLETION = 200; // vanilla value is 200 = 10 seconds

    public double fractionOfCookTimeComplete() {
        double fraction = cookTime / (double) COOK_TIME_FOR_COMPLETION;
        return MathHelper.clamp(fraction, 0.0, 1.0);
    }

    // OVERRIDE IF AUTOMATABLE
    @Override
    public List<Integer> inputSlots() {
        return Arrays.asList();
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        int[] ar = new int[slots().size()];
        for (int i = 0; i < slots().size(); i++) {
            ar[i] = i;
        }
        return ar;

    }

    private List<Integer> slots() {

        List<Integer> slots = new ArrayList<>();

        for (int i = 0; i < itemStacks.length; i++) {
            slots.add(i);
        }

        return slots;
    }

    public boolean containsSlot(int index) {
        return inputSlots().contains(index);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, Direction direction) {
        if (this.isAutomatable() && containsSlot(index)) {
            // don't insert shit
            return this.isItemValidInput(itemStackIn);
        }
        return false;
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {

        if (this.isAutomatable()) {
            return isOutputSlot(index);
        }
        return false;
    }

    // Gets the stack in the given slot
    @Override
    public ItemStack getItem(int i) {

        return itemStacks[i];
    }

    /**
     * Removes some of the units from itemstack in the given slot, and returns as a
     * separate itemstack
     *
     * @param slotIndex the slot number to remove the items from
     * @param count     the number of units to remove
     * @return a new itemstack containing the units removed from the slot
     */
    @Override
    public ItemStack removeItem(int slotIndex, int count) {
        ItemStack itemStackInSlot = getItem(slotIndex);
        if (itemStackInSlot.isEmpty())
            return ItemStack.EMPTY; // isEmpty(), EMPTY_ITEM

        ItemStack itemStackRemoved;
        if (itemStackInSlot.getCount() <= count) { // getStackSize
            itemStackRemoved = itemStackInSlot;
            setItem(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
        } else {
            itemStackRemoved = itemStackInSlot.split(count);
            if (itemStackInSlot.getCount() == 0) { // getStackSize
                setItem(slotIndex, ItemStack.EMPTY); // EMPTY_ITEM
            }
        }
        setChanged();
        return itemStackRemoved;
    }

    // Gets the number of slots in the inventory
    @Override
    public int getContainerSize() {
        return itemStacks.length;
    }

    // returns true if all of the slots in the inventory are empty
    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : itemStacks) {
            if (!itemstack.isEmpty()) { // isEmpty()
                return false;
            }
        }

        return true;
    }

    // overwrites the stack in the given slotIndex with the given stack
    @Override
    public void setItem(int slotIndex, ItemStack itemstack) {
        itemStacks[slotIndex] = itemstack;
        if (!itemstack.isEmpty() && itemstack.getCount() > getMaxStackSize()) { // isEmpty(); getStackSize()
            itemstack.setCount(getMaxStackSize()); // setStackSize()
        }
        setChanged();
    }

    // set all slots to empty
    @Override
    public void clearContent() {
        Arrays.fill(itemStacks, ItemStack.EMPTY); // EMPTY_ITEM
    }

    @Override
    public void startOpen(PlayerEntity player) {
    }

    @Override
    public void stopOpen(PlayerEntity player) {
    }

    // -----------------------------------------------------------------------------------------------------------
    // The following methods are not needed for this example but are part of
    // IInventory so they must be implemented

    // Unused unless your container specifically uses it.
    // Return true if the given stack is allowed to go in the given slot
    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack itemstack) {
        return true;
    }

    /**
     * This method removes the entire contents of the given slot and returns it.
     * Used by containers such as crafting tables which return any items in their
     * slots when you close the GUI
     *
     * @param slotIndex
     * @return
     */
    @Override
    public ItemStack removeItemNoUpdate(int slotIndex) {

        ItemStack itemStack = getItem(slotIndex);
        if (!itemStack.isEmpty())
            setItem(slotIndex, ItemStack.EMPTY); // isEmpty(); EMPTY_ITEM
        return itemStack;
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public boolean stillValid(PlayerEntity player) {
        if (this.level.getBlockEntity(this.worldPosition) != this)
            return false;
        final double X_CENTRE_OFFSET = 0.5;
        final double Y_CENTRE_OFFSET = 0.5;
        final double Z_CENTRE_OFFSET = 0.5;
        final double MAXIMUM_DISTANCE_SQ = 8.0 * 8.0;
        return player.distanceToSqr(worldPosition.getX() + X_CENTRE_OFFSET, worldPosition.getY() + Y_CENTRE_OFFSET, worldPosition
            .getZ() + Z_CENTRE_OFFSET) < MAXIMUM_DISTANCE_SQ;
    }

    @Override
    public CompoundNBT save(CompoundNBT parentNBTTagCompound) {
        super.save(parentNBTTagCompound); // The super call is required to save and load the tiles location

        ListNBT dataForAllSlots = new ListNBT();
        for (int i = 0; i < this.itemStacks.length; ++i) {
            if (!this.itemStacks[i].isEmpty()) { // isEmpty()
                CompoundNBT dataForThisSlot = new CompoundNBT();
                dataForThisSlot.putByte("Slot", (byte) i);
                this.itemStacks[i].save(dataForThisSlot);
                dataForAllSlots.add(dataForThisSlot);
            }
        }
        // the array of hashmaps is then inserted into the instance hashmap for the
        // container
        parentNBTTagCompound.put("Items", dataForAllSlots);
        parentNBTTagCompound.putInt("ticks", ticks);

        // Save everything else
        parentNBTTagCompound.putShort("CookTime", cookTime);

        parentNBTTagCompound.putInt("fuel", this.fuel);
        return parentNBTTagCompound;
    }

    // This is where you load the dataInstance that you saved in write
    @Override
    public void load(BlockState state, CompoundNBT nbtTagCompound) {
        super.load(state, nbtTagCompound); // The super call is required to save and load the tiles location
        final byte NBT_TYPE_COMPOUND = 10; // See NBTBase.createNewByType() for a listing
        ListNBT dataForAllSlots = nbtTagCompound.getList("Items", NBT_TYPE_COMPOUND);

        Arrays.fill(itemStacks, ItemStack.EMPTY); // set all slots to empty EMPTY_ITEM
        for (int i = 0; i < dataForAllSlots.size(); ++i) {
            CompoundNBT dataForOneSlot = dataForAllSlots.getCompound(i);
            byte slotNumber = dataForOneSlot.getByte("Slot");
            if (slotNumber >= 0 && slotNumber < this.itemStacks.length) {
                this.itemStacks[slotNumber] = ItemStack.of(dataForOneSlot);
            }
        }

        // Load everything else. Trim the arrays (or pad with 0) to make sure they have
        // the correct number of elements
        cookTime = nbtTagCompound.getShort("CookTime");
        ticks = nbtTagCompound.getInt("ticks");
        this.fuel = nbtTagCompound.getInt("fuel");
    }

}
