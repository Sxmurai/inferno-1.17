package me.sxmurai.inferno.util.math;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

public class VecUtil {
    public static Vec3d toVec3d(Vec3i vec) {
        return new Vec3d(vec.getX(), vec.getY(), vec.getZ());
    }
}
