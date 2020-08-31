package com.robertx22.library_of_exile.tile_bases;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseInventoryBlock extends NonFullBlock implements BlockEntityProvider {

    protected BaseInventoryBlock(Settings prop) {
        super(prop);

    }

    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return (NamedScreenHandlerFactory) world.getBlockEntity(pos);
    }

    @Override
    @Deprecated
    public List<ItemStack> getDroppedStacks(BlockState blockstate, LootContext.Builder context) {

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(this));

        BlockEntity tileentity = context.getNullable(LootContextParameters.BLOCK_ENTITY);

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
    @Environment(EnvType.CLIENT)
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView reader, BlockPos pos) {
        return true;
    }

    public abstract Identifier getContainerId();

    @Override
    @Deprecated
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                              Hand hand, BlockHitResult ray) {
        if (world.isClient) {
            return ActionResult.CONSUME;
        }

        BlockEntity tile = world.getBlockEntity(pos);

        if (tile instanceof BaseTile) {

            ContainerProviderRegistry.INSTANCE.openContainer(getContainerId(), player, buf -> buf.writeBlockPos(pos));

            return ActionResult.SUCCESS;
        }

        return ActionResult.CONSUME;
    }

}
