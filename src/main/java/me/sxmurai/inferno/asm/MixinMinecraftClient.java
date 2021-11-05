package me.sxmurai.inferno.asm;

import me.sxmurai.inferno.Inferno;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    public void hookGetWindowTitle(CallbackInfoReturnable<String> info) {
        info.setReturnValue(Inferno.loaded ? "Inferno v1.0.0" : "Loading Inferno v1.0.0");
    }
}
