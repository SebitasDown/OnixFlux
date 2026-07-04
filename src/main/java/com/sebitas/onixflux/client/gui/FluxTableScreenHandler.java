package com.sebitas.onixflux.client.gui;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.network.dispatcher.ClientDispatcher;
import com.sebitas.onixflux.network.packet.CreateItemPacket;
import com.sebitas.onixflux.network.packet.LearnItemPacket;
import com.sebitas.onixflux.player.PlayerDataManager;
import com.sebitas.onixflux.transmutation.*;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public class FluxTableScreenHandler {

    private final FluxTableMenu menu;
    private final FluxTableState state;
    private final Consumer<Runnable> refreshCallback;

    private List<FluxItemEntry> allEntries;
    private List<FluxItemEntry> filteredEntries;
    private int maxPage;

    private long playerFx;
    private long maxFx;

    public FluxTableScreenHandler(FluxTableMenu menu, Consumer<Runnable> refreshCallback) {
        this.menu = menu;
        this.state = new FluxTableState();
        this.refreshCallback = refreshCallback;
        this.maxFx = Long.MAX_VALUE;
    }

    public void refreshEntries() {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var learned = PlayerDataManager.getLearnedItems(mc.player);
        allEntries = FluxSearchEngine.buildEntries(learned);
        applyFilters();
        updatePlayerFx();
    }

    private void applyFilters() {
        String query = state.getSearchQuery();
        String category = state.getSelectedCategory();
        filteredEntries = FluxSearchEngine.search(allEntries, query, category);
        maxPage = FluxSearchEngine.getMaxPage(filteredEntries, getItemsPerPage());
        if (state.getCurrentPage() >= maxPage) {
            state.setCurrentPage(Math.max(0, maxPage - 1));
        }
    }

    private void updatePlayerFx() {
        var mc = Minecraft.getInstance();
        if (mc.player != null) {
            playerFx = PlayerDataManager.getFlux(mc.player);
        }
    }

    public void handleLearn() {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;

        var stack = menu.getBlockEntity().getInventory().getStackInSlot(0);
        if (stack.isEmpty()) return;

        ClientDispatcher.sendToServer(new LearnItemPacket(menu.containerId));
        refreshEntries();
    }

    public void handleTransmute(int count) {
        Item selected = state.getSelectedItem();
        if (selected == null) return;

        ClientDispatcher.sendToServer(new CreateItemPacket(menu.containerId, selected, count));
        refreshEntries();
    }

    public void handleExtract(int slotIndex, boolean shift, boolean ctrl) {
        if (slotIndex < 0 || slotIndex >= filteredEntries.size()) return;
        Item item = filteredEntries.get(slotIndex).item();
        state.setSelectedItem(item);

        int count;
        if (ctrl) {
            count = 64;
        } else if (shift) {
            var stack = new ItemStack(item);
            count = stack.getMaxStackSize();
        } else {
            count = 1;
        }
        handleTransmute(count);
    }

    public void setSearchQuery(String query) {
        state.setSearchQuery(query);
        refreshEntries();
    }

    public void setCategory(String category) {
        state.setSelectedCategory(category);
        refreshEntries();
    }

    public void nextPage() {
        if (state.getCurrentPage() < maxPage - 1) {
            state.setCurrentPage(state.getCurrentPage() + 1);
            state.setScrollOffset(0);
            refreshEntries();
        }
    }

    public void prevPage() {
        if (state.getCurrentPage() > 0) {
            state.setCurrentPage(state.getCurrentPage() - 1);
            state.setScrollOffset(0);
            refreshEntries();
        }
    }

    public void scrollBy(int delta) {
        int newScroll = state.getScrollOffset() - delta;
        int maxScroll = Math.max(0, (filteredEntries.size() + getGridCols() - 1) / getGridCols() * 18 - getGridHeight());
        state.setScrollOffset(Math.max(0, Math.min(newScroll, maxScroll)));
    }

    public List<FluxItemEntry> getCurrentPageEntries() {
        return FluxSearchEngine.getPage(filteredEntries, state.getCurrentPage(), getItemsPerPage());
    }

    public boolean hasPrevPage() { return state.getCurrentPage() > 0; }
    public boolean hasNextPage() { return state.getCurrentPage() < maxPage - 1; }
    public int currentPage() { return state.getCurrentPage(); }
    public int maxPage() { return Math.max(1, maxPage); }
    public long playerFx() { return playerFx; }
    public long maxFx() { return maxFx; }
    public FluxTableState state() { return state; }
    public List<FluxItemEntry> filteredEntries() { return filteredEntries; }
    public int getItemsPerPage() { return getGridCols() * getGridRows(); }
    public int getGridCols() { return 8; }
    public int getGridRows() { return 5; }
    public int getGridHeight() { return getGridRows() * 18; }

    public long getFxValue(Item item) {
        return FluxEngine.getValue(item).map(v -> v.value()).orElse(0L);
    }

}
