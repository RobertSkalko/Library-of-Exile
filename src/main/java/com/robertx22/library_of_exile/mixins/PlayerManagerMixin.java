package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.management.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerManagerMixin {

    @Inject(method = "placeNewPlayer(Lnet/minecraft/network/NetworkManager;Lnet/minecraft/entity/player/ServerPlayerEntity;)V", at = @At(value = "RETURN"))
    public void hook(NetworkManager connection, ServerPlayerEntity player, CallbackInfo ci) {
        try {
            ExileEvents.ON_PLAYER_LOGIN.callEvents(new ExileEvents.OnPlayerLogin(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
