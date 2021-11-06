package me.sxmurai.inferno.util.entity;

import me.sxmurai.inferno.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

import java.util.HashMap;
import java.util.Map;

public class InventoryUtil implements Util {
    public static final int OFFHAND_SLOT = 45;

    public static int getBlockHotbar(Block block) {
        if (mc.player.getOffHandStack().getItem() instanceof BlockItem && ((BlockItem) mc.player.getOffHandStack().getItem()).getBlock() == block) {
            return InventoryUtil.OFFHAND_SLOT;
        }

        for (Map.Entry<Integer, ItemStack> slot : InventoryUtil.getItems(0, 9).entrySet()) {
            ItemStack stack = slot.getValue();
            if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == block) {
                return slot.getKey();
            }
        }

        return -1;
    }

    public static Map<Integer, ItemStack> getItems(int start, int end) {
        Map<Integer, ItemStack> slots = new HashMap<>();
        for (int i = start; i < end; ++i) {
            slots.put(i, mc.player.getInventory().getStack(i));
        }

        return slots;
    }

    public static void swap(int slot) {
        mc.player.getInventory().selectedSlot = slot;
        mc.interactionManager.tick(); // update
    }

    public static class SwapHandler {
        private int oldSlot = -1;
        private Hand hand;

        public void swap(int slot) {
            this.hand = slot == 45 ? Hand.OFF_HAND : Hand.MAIN_HAND;
            if (this.hand == Hand.MAIN_HAND) {
                InventoryUtil.swap(slot);
            }
        }

        public void reset() {
            if (this.oldSlot != -1) {
                InventoryUtil.swap(this.oldSlot);
            }

            this.oldSlot = -1;
            this.hand = null;
        }

        public int getOldSlot() {
            return oldSlot;
        }

        public Hand getHand() {
            return hand;
        }
    }
}
