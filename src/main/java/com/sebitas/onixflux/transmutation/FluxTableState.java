package com.sebitas.onixflux.transmutation;

import net.minecraft.world.item.Item;

public final class FluxTableState {

    private Item selectedItem;
    private String searchQuery;
    private String selectedCategory;
    private int currentPage;
    private int itemsPerPage;
    private int scrollOffset;

    public FluxTableState() {
        this.selectedItem = null;
        this.searchQuery = "";
        this.selectedCategory = "all";
        this.currentPage = 0;
        this.itemsPerPage = 36;
        this.scrollOffset = 0;
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item item) {
        this.selectedItem = item;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        this.searchQuery = query != null ? query : "";
        this.currentPage = 0;
        this.scrollOffset = 0;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String category) {
        this.selectedCategory = category != null ? category : "all";
        this.currentPage = 0;
        this.scrollOffset = 0;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int page) {
        this.currentPage = Math.max(0, page);
    }

    public void nextPage(int maxPage) {
        if (currentPage < maxPage - 1) currentPage++;
    }

    public void previousPage() {
        if (currentPage > 0) currentPage--;
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setScrollOffset(int offset) {
        this.scrollOffset = Math.max(0, offset);
    }

    public void reset() {
        this.selectedItem = null;
        this.currentPage = 0;
        this.scrollOffset = 0;
    }

}
