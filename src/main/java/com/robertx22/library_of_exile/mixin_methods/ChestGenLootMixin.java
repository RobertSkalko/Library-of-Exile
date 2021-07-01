package com.robertx22.library_of_exile.mixin_methods;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChestGenLootMixin {

    public static void onLootGen(Inventory inventory, LootContext context) {
        BlockEntity chest = null;
        BlockPos pos = null;

        if (inventory instanceof BlockEntity) {
            chest = (BlockEntity) inventory;
        }

        if (context.hasParameter(LootContextParameters.THIS_ENTITY) && context.get(LootContextParameters.THIS_ENTITY) instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) context.get(LootContextParameters.THIS_ENTITY);
            World world = null;
            if (chest != null) {
                world = chest.getWorld();
                pos = chest.getPos();
            }
            if (world == null) {
                return;
            }

            if (inventory instanceof ChestBlockEntity) {
                ExileEvents.ON_CHEST_LOOTED.callEvents(new ExileEvents.OnChestLooted(player, context, inventory, pos));
            }

        }
    }

}
