package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.events.base.ExileEvents;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {

    @Inject(method = "onPlayerConnect", at = @At(value = "RETURN"))
    public void hook(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        try {
            ExileEvents.ON_PLAYER_LOGIN.callEvents(new ExileEvents.OnPlayerLogin(player));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
