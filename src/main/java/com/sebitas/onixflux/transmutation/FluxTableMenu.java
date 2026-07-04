package com.sebitas.onixflux.transmutation;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FluxTableMenu extends AbstractContainerMenu {

    private final FluxTableBlockEntity blockEntity;

    public FluxTableMenu(int id, Inventory inv, FluxTableBlockEntity be) {
        super(ModMenuTypes.FLUX_TABLE.get(), id);
        this.blockEntity = be;

        addSlot(new FluxTableSlots.LearnSlot(be.getInventory(), 0, 152, 17));
        addSlot(new FluxTableSlots.OutputSlot(be.getInventory(), 1, 152, 53));

        int invLeft = 8;
        int invTop = 140;
        int hotbarTop = 198;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inv, col + row * 9 + 9, invLeft + col * 18, invTop + row * 18));
            }
        }

        for (int col = 0; col < 9; col++) {
            addSlot(new Slot(inv, col, invLeft + col * 18, hotbarTop));
        }
    }

    public FluxTableMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(id, inv, (FluxTableBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public FluxTableBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot slot = slots.get(index);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack result = stack.copy();

        if (index < 2) {
            if (!moveItemStackTo(stack, 2, 38, true)) return ItemStack.EMPTY;
        } else {
            if (FluxEngine.hasValue(stack.getItem())) {
                if (!moveItemStackTo(stack, 0, 1, false)) return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        return result;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }

}
