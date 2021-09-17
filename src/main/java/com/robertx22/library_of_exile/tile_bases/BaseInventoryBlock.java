package com.robertx22.library_of_exile.tile_bases;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseInventoryBlock extends NonFullBlock implements ITileEntityProvider {

    protected BaseInventoryBlock(Properties prop) {
        super(prop);

    }

    @Override
    public INamedContainerProvider getMenuProvider(BlockState state, World world, BlockPos pos) {
        return (INamedContainerProvider) world.getBlockEntity(pos);
    }

    @Override
    @Deprecated
    public List<ItemStack> getDrops(BlockState blockstate, LootContext.Builder context) {

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this));

        TileEntity tileentity = context.getOptionalParameter(LootParameters.BLOCK_ENTITY);

        if (tileentity instanceof BaseTile) {

            BaseTile inv = (BaseTile) tileentity;

            for (ItemStack stack : inv.itemStacks) {
                if (stack.isEmpty() == false) {
                    items.add(stack);
                }
            }

        }

        return items;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getShadeBrightness(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    public abstract ResourceLocation getContainerId();

    @Override
    @Deprecated
    public RayTraceResult use(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockRayTraceResult ray) {
        if (world.isClientSide) {
            return InteractionResult.CONSUME;
        }

        TileEntity tile = world.getBlockEntity(pos);

        if (tile instanceof BaseTile) {

            ContainerProviderRegistry.INSTANCE.openContainer(getContainerId(), player, buf -> buf.writeBlockPos(pos));

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }

}
