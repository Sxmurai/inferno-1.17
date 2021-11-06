package me.sxmurai.inferno.util.world;

import me.sxmurai.inferno.util.Util;
import me.sxmurai.inferno.util.math.VecUtil;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class BlockUtil implements Util {
    public static void place(Placement placement, BlockPos pos, Hand hand, boolean swing, boolean sneak, boolean rotate) {
        Direction direction = BlockUtil.getFacingNoRaytrace(pos);
        if (direction == null) {
            return;
        }

        if (sneak) {
            mc.player.setSneaking(true);
        }

        BlockPos neighbor = pos.offset(direction);
        Vec3d hitVec = new Vec3d(neighbor.getX() + 0.5, neighbor.getY() + 0.5, neighbor.getZ() + 0.5).add(VecUtil.toVec3d(direction.getOpposite().getVector()).multiply(0.5));

        if (rotate) {
            // @todo
        }

        BlockHitResult result = new BlockHitResult(hitVec, direction.getOpposite(), neighbor, false);
        if (placement == Placement.Vanilla) {
            mc.interactionManager.interactBlock(mc.player, mc.world, hand, result);
        } else if (placement == Placement.Packet) {
            mc.player.networkHandler.sendPacket(new PlayerInteractBlockC2SPacket(hand, result));
        }

        if (swing) {
            mc.player.swingHand(hand);
        } else {
            mc.player.networkHandler.sendPacket(new HandSwingC2SPacket(hand));
        }

        if (sneak) {
            mc.player.setSneaking(false);
        }
    }

    public static Direction getFacingNoRaytrace(BlockPos pos) {
        for (Direction direction : Direction.values()) {
            BlockPos neighbor = pos.offset(direction);
            if (mc.world.isAir(neighbor)) {
                continue;
            }

            return direction;
        }

        return null;
    }

    public enum Placement {
        Vanilla, Packet
    }
}
