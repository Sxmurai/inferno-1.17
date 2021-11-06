package me.sxmurai.inferno.impl.features.module.modules.combat;

import me.sxmurai.inferno.impl.features.module.Module;
import me.sxmurai.inferno.impl.settings.Setting;
import me.sxmurai.inferno.util.entity.InventoryUtil;
import me.sxmurai.inferno.util.world.BlockUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.glfw.GLFW;

@Module.Define(name = "SelfFill", category = Module.Category.Combat)
@Module.Info(description = "Lags you back into a block", bind = GLFW.GLFW_KEY_C)
public class SelfFill extends Module {
    public final Setting<Type> type = new Setting<>("Type", Type.Obsidian);
    public final Setting<Double> rubberband = new Setting<>("Rubberband", 3.0, -5.0, 5.0);
    public final Setting<Boolean> rotate = new Setting<>("Rotate", true);
    public final Setting<Boolean> swing = new Setting<>("Swing", true);

    private final InventoryUtil.SwapHandler swapHandler = new InventoryUtil.SwapHandler();
    private BlockPos origin;

    @Override
    protected void onActivated() {
        if (nullCheck()) {
            this.origin = mc.player.getBlockPos();
            if (!mc.world.isAir(this.origin.add(0.0, 1.0, 0.0)) || !mc.world.isAir(this.origin.add(0.0, 2.0, 0.0))) { // not enough headspace
                this.toggle();
                return;
            }

            int slot = InventoryUtil.getBlockHotbar(this.type.getValue().getBlock());
            if (slot == -1) {
                this.toggle();
                return;
            }

            this.swapHandler.swap(slot);
        } else {
            this.toggle();
        }
    }

    @Override
    protected void onDeactivated() {
        this.origin = null;
        this.swapHandler.reset();
    }

    @Override
    public void onTick() {
        if (this.origin != null) {
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.origin.getX(), this.origin.getY() + 0.41999998688698, this.origin.getZ(), true));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.origin.getX(), this.origin.getY() + 0.7531999805211997, this.origin.getZ(), true));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.origin.getX(), this.origin.getY() + 1.00133597911214, this.origin.getZ(), true));
            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.origin.getX(), this.origin.getY() + 1.16610926093821, this.origin.getZ(), true));

            BlockUtil.place(BlockUtil.Placement.Packet, this.origin, this.swapHandler.getHand(), this.swing.getValue(), true, this.rotate.getValue());

            mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(this.origin.getX(), this.origin.getY() + this.rubberband.getValue(), this.origin.getZ(), true));

            this.toggle();
        }
    }

    public enum Type {
        Obsidian(Blocks.OBSIDIAN),
        EnderChest(Blocks.ENDER_CHEST),
        EndRod(Blocks.END_ROD),
        Chest(Blocks.CHEST);

        private final Block block;
        Type(Block block) {
            this.block = block;
        }

        public Block getBlock() {
            return block;
        }
    }
}
