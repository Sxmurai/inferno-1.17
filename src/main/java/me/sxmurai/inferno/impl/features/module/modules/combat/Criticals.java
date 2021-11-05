package me.sxmurai.inferno.impl.features.module.modules.combat;

import me.sxmurai.inferno.asm.network.packet.c2s.IPlayerInteractEntityC2SPacket;
import me.sxmurai.inferno.impl.events.network.PacketEvent;
import me.sxmurai.inferno.impl.features.module.Module;
import me.sxmurai.inferno.impl.settings.Setting;
import me.zero.alpine.listener.Listener;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

@Module.Define(name = "Criticals", category = Module.Category.Combat)
@Module.Info(description = "Scores critical hits for you", bind = GLFW.GLFW_KEY_C)
public class Criticals extends Module {
    public final Setting<Mode> mode = new Setting<>("Mode", Mode.Packet);

    private final Listener<PacketEvent.Send> packetSendListener = new Listener<>((event) -> {
        if (event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            PlayerInteractEntityC2SPacket packet = event.getPacket();
            if (((IPlayerInteractEntityC2SPacket) packet).getType().getType() == PlayerInteractEntityC2SPacket.InteractType.ATTACK) {
                switch (this.mode.getValue()) {
                    case Packet -> {
                        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() + 0.3, mc.player.getZ(), false));
                        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
                    }

                    case Bypass -> {
                        // @todo - i assume NCP has updated... right??
                    }

                    case MiniJump -> {
                        Vec3d velocity = mc.player.getVelocity();
                        mc.player.setVelocityClient(velocity.x, 0.2, velocity.z);
                        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY(), mc.player.getZ(), false));
                    }

                    case Jump -> {
                        mc.player.jump();
                        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(mc.player.getX(), mc.player.getY() - 0.05, mc.player.getZ(), false));
                    }
                }
            }
        }
    });

    public enum Mode {
        Packet, Bypass, MiniJump, Jump
    }
}
