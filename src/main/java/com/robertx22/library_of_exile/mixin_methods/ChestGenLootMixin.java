package com.robertx22.library_of_exile.mixin_methods;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestGenLootMixin {

    public static void onLootGen(IInventory inventory, LootContext context) {
        TileEntity chest = null;
        BlockPos pos = null;

        if (inventory instanceof TileEntity) {
            chest = (TileEntity) inventory;
        }

        if (context.hasParam(LootParameters.THIS_ENTITY) && context.getParamOrNull(LootParameters.THIS_ENTITY) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) context.getParamOrNull(LootParameters.THIS_ENTITY);
            World world = null;
            if (chest != null) {
                world = chest.getLevel();
                pos = chest.getBlockPos();
            }
            if (world == null) {
                return;
            }

            if (inventory instanceof ChestTileEntity) {
                ExileEvents.ON_CHEST_LOOTED.callEvents(new ExileEvents.OnChestLooted(player, context, inventory, pos));
            }

        }
    }

}
