package com.robertx22.library_of_exile.mixins;

import com.robertx22.library_of_exile.registry.ExileRegistryType;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.command.CommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerResourceManager.class)
public abstract class ServerResourceManagerMixin {

    @Inject(method = "<init>*", at = @At("RETURN"))
    public void onConstructRegisterAllReloaders(CommandManager.RegistrationEnvironment registrationEnvironment, int i, CallbackInfo ci) {
        ServerResourceManager m = (ServerResourceManager) (Object) this;
        ReloadableResourceManager manager = (ReloadableResourceManager) m.getResourceManager();
        ExileRegistryType.registerJsonListeners(manager);
    }

}
