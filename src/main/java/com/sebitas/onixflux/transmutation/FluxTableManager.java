package com.sebitas.onixflux.transmutation;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public final class FluxTableManager {

    private static final Component TITLE = Component.translatable("container.onixflux.flux_table");

    private FluxTableManager() {}

    public static void openTable(Player player, BlockPos pos) {
        if (player.level().isClientSide) return;

        BlockEntity be = player.level().getBlockEntity(pos);
        if (!(be instanceof FluxTableBlockEntity tableBE)) return;

        player.openMenu(new MenuProvider() {
            @Override
            public @NotNull Component getDisplayName() {
                return TITLE;
            }

            @Override
            public AbstractContainerMenu createMenu(int id, @NotNull Inventory inv, @NotNull Player p) {
                return new FluxTableMenu(id, inv, tableBE);
            }
        });
    }

}
