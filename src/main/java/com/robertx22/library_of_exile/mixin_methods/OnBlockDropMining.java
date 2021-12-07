package com.robertx22.library_of_exile.mixin_methods;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class OnBlockDropMining {

    public static void run(LootContext ctx, CallbackInfoReturnable<List<ItemStack>> ci) {

        try {
            if (!ctx.hasParam(LootParameters.BLOCK_STATE)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.TOOL)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.ORIGIN)) {
                return;
            }
            if (!ctx.hasParam(LootParameters.THIS_ENTITY)) {
                return;
            }

            ItemStack stack = ctx.getParamOrNull(LootParameters.TOOL);
            if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0) {
                return;
            }

            BlockState state = ctx.getParamOrNull(LootParameters.BLOCK_STATE);
            Block block = state.getBlock();

            if (ci.getReturnValue()
                .stream()
                .anyMatch(x -> x.getItem() == block.asItem())) {
                return; // if a diamond ore is broken and drops diamond ore, don't give exp and loot
            }

            Entity en = ctx.getParamOrNull(LootParameters.THIS_ENTITY);

            BlockPos pos = new BlockPos(ctx.getParamOrNull(LootParameters.ORIGIN));

            PlayerEntity player = null;
            if (en instanceof PlayerEntity) {
                player = (PlayerEntity) en;
            } else {
                return;
            }
            if (player.level.isClientSide) {
                return;
            }

            ExileEvents.PlayerMineOreEvent event = new ExileEvents.PlayerMineOreEvent(state, player, pos);

            ExileEvents.PLAYER_MINE_ORE.callEvents(event);

            if (!event.itemsToAddToDrop.isEmpty()) {
                ci.getReturnValue()
                    .addAll(event.itemsToAddToDrop);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}