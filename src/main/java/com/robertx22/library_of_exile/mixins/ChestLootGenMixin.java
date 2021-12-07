package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.mixin_methods.ChestGenLootMixin;
import com.robertx22.library_of_exile.mixin_methods.OnBlockDropMining;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootTable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LootTable.class)
public abstract class ChestLootGenMixin {

    @Inject(method = "fill", at = @At(value = "TAIL"))
    public void onLootGen(IInventory inventory, LootContext context, CallbackInfo ci) {
        try {

            ChestGenLootMixin.onLootGen(inventory, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = "getRandomItems(Lnet/minecraft/loot/LootContext;)Ljava/util/List;", at = @At(value = "TAIL"))
    public void onLootGen(LootContext context, CallbackInfoReturnable<List<ItemStack>> ci) {
        try {
            OnBlockDropMining.run(context, ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}