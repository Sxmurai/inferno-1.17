package me.sxmurai.inferno.asm.input;

import me.sxmurai.inferno.Inferno;
import me.sxmurai.inferno.impl.events.input.KeyEvent;
import me.sxmurai.inferno.impl.features.Wrapper;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class MixinKeyboard {
    @Inject(method = "onKey", at = @At("HEAD"))
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo info) {
        if (key != GLFW.GLFW_KEY_UNKNOWN && Wrapper.mc.currentScreen == null) {
            Inferno.EVENT_BUS.post(new KeyEvent(KeyEvent.Action.fromAction(action), key, scancode));
        }
    }
}
