package com.sebitas.onixflux.transmutation;

import com.sebitas.onixflux.fx.FluxEngine;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public final class FluxTableSlots {

    private FluxTableSlots() {}

    public static class LearnSlot extends SlotItemHandler {

        public LearnSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return !stack.isEmpty() && FluxEngine.hasValue(stack.getItem());
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }

    }

    public static class OutputSlot extends SlotItemHandler {

        public OutputSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(@NotNull ItemStack stack) {
            return false;
        }

        @Override
        public int getMaxStackSize() {
            return 64;
        }

        @Override
        public void onTake(@NotNull Player player, @NotNull ItemStack stack) {
            super.onTake(player, stack);
        }

    }

}
