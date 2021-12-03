package com.robertx22.library_of_exile.mixin_methods;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestGenLootMixin {

    public static void onLootGen(IInventory inventory, LootContext context) {

        try {
            if (context.hasParam(LootParameters.THIS_ENTITY) &&
                context.hasParam(LootParameters.ORIGIN)
                && context.getParamOrNull(LootParameters.THIS_ENTITY) instanceof PlayerEntity) {

                TileEntity chest = null;
                BlockPos pos = new BlockPos(context.getParamOrNull(LootParameters.ORIGIN));

                PlayerEntity player = (PlayerEntity) context.getParamOrNull(LootParameters.THIS_ENTITY);
                World world = player.level;

                if (inventory instanceof TileEntity) {
                    chest = (TileEntity) inventory;
                }
                if (chest == null) {
                    chest = world.getBlockEntity(pos);
                }
                if (world == null) {
                    return;
                }
                if (chest instanceof LockableLootTileEntity) {
                    ExileEvents.ON_CHEST_LOOTED.callEvents(new ExileEvents.OnChestLooted(player, context, inventory, pos));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
