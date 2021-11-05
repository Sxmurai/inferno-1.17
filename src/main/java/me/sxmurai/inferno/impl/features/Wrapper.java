package me.sxmurai.inferno.impl.features;

import net.minecraft.client.MinecraftClient;

public interface Wrapper {
    MinecraftClient mc = MinecraftClient.getInstance();

    default boolean nullCheck() {
        return mc.player != null && mc.world != null;
    }
}
