package me.sxmurai.inferno.asm.network.packet.c2s;

import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PlayerInteractEntityC2SPacket.class)
public interface IPlayerInteractEntityC2SPacket {
    // what in the actual ass mojang
    // there has to be a better way to do this why the fuck do i have to use fjhkfjhfdskjafhfdkj
    @Accessor
    PlayerInteractEntityC2SPacket.InteractTypeHandler getType();
}
