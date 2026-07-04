package com.sebitas.onixflux.client.gui;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.network.dispatcher.ClientDispatcher;
import com.sebitas.onixflux.network.packet.CreateItemPacket;
import com.sebitas.onixflux.network.packet.LearnItemPacket;
import com.sebitas.onixflux.player.PlayerDataManager;
import com.sebitas.onixflux.transmutation.*;
import com.sebitas.onixflux.transmutation.FluxCategoryManager.FluxCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class FluxTableScreen extends AbstractContainerScreen<FluxTableMenu> {

    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "textures/gui/flux_table.png");
    private static final int GRID_COLS = 6;
    private static final int GRID_ROWS = 4;
    private static final int ITEMS_PER_PAGE = GRID_COLS * GRID_ROWS;

    private final FluxTableState state = new FluxTableState();
    private EditBox searchBox;
    private List<FluxItemEntry> allEntries;
    private List<FluxItemEntry> filteredEntries;
    private List<FluxItemEntry> pageEntries;
    private int maxPage = 1;

    public FluxTableScreen(FluxTableMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 220;
        this.imageHeight = 226;
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = this.imageHeight - 92;
        this.titleLabelX = 8;
        this.titleLabelY = 6;

        FluxCategoryManager.initialize();
        refreshEntries();

        int searchLeft = leftPos + 7;
        int searchTop = topPos + 22;
        this.searchBox = new EditBox(font, searchLeft, searchTop, 124, 12, Component.translatable("gui.onixflux.search"));
        this.searchBox.setBordered(false);
        this.searchBox.setMaxLength(50);
        this.searchBox.setTextColor(0xFFFFFF);
        this.searchBox.visible = true;
        this.searchBox.setCanLoseFocus(true);
        addRenderableWidget(searchBox);
    }

    @Override
    public void resize(Minecraft mc, int w, int h) {
        String query = searchBox.getValue();
        super.resize(mc, w, h);
        searchBox.setValue(query);
    }

    private void refreshEntries() {
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var learned = PlayerDataManager.getLearnedItems(mc.player);
        allEntries = FluxSearchEngine.buildEntries(learned);
        applyFilters();
    }

    private void applyFilters() {
        String query = state.getSearchQuery();
        String category = state.getSelectedCategory();
        filteredEntries = FluxSearchEngine.search(allEntries, query, category);
        maxPage = FluxSearchEngine.getMaxPage(filteredEntries, ITEMS_PER_PAGE);
        if (state.getCurrentPage() >= maxPage) {
            state.setCurrentPage(maxPage - 1);
        }
        pageEntries = FluxSearchEngine.getPage(filteredEntries, state.getCurrentPage(), ITEMS_PER_PAGE);
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        renderBackground(gui);
        gui.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight, 256, 256);

        gui.fill(leftPos + 7, topPos + 37, leftPos + 7 + 124, topPos + 37 + 1, 0xFF555555);

        int gridLeft = leftPos + 7;
        int gridTop = topPos + 44;
        int gridWidth = GRID_COLS * 18;
        int gridHeight = GRID_ROWS * 18;

        gui.fill(gridLeft, gridTop, gridLeft + gridWidth, gridTop + gridHeight, 0x33000000);

        gui.enableScissor(gridLeft, gridTop, gridLeft + gridWidth, gridTop + gridHeight);

        int yOff = -state.getScrollOffset();
        for (int i = 0; i < pageEntries.size(); i++) {
            FluxItemEntry entry = pageEntries.get(i);
            int col = i % GRID_COLS;
            int row = i / GRID_COLS;
            int x = gridLeft + col * 18;
            int y = gridTop + row * 18 + yOff;

            boolean selected = state.getSelectedItem() == entry.item();
            if (selected) {
                gui.fill(x, y, x + 18, y + 18, 0x55FFFF00);
            } else {
                gui.fill(x, y, x + 18, y + 18, 0x22FFFFFF);
            }

            ItemStack stack = new ItemStack(entry.item());
            gui.renderItem(stack, x + 1, y + 1);
        }

        gui.disableScissor();

        int rightCol = leftPos + 140;

        long playerFX = 0;
        var mc = Minecraft.getInstance();
        if (mc.player != null) {
            playerFX = PlayerDataManager.getFlux(mc.player);
        }

        Component fxText = Component.translatable("gui.onixflux.fx", playerFX);
        gui.drawString(font, fxText, rightCol, topPos + 6, 0xFFAAFF, false);

        Item selected = state.getSelectedItem();
        if (selected != null) {
            ItemStack selStack = new ItemStack(selected);
            gui.renderItem(selStack, rightCol, topPos + 24);
            gui.drawString(font, selStack.getHoverName(), rightCol + 20, topPos + 24, 0xFFFFFF, false);

            long fxValue = FluxEngine.getValue(selected).map(v -> v.value()).orElse(0L);
            Component valText = Component.translatable("gui.onixflux.value", fxValue);
            gui.drawString(font, valText, rightCol, topPos + 44, 0xAAAAAA, false);
        } else {
            Component noneText = Component.translatable("gui.onixflux.no_selection");
            gui.drawString(font, noneText, rightCol, topPos + 24, 0x666666, false);
        }

        String pageInfo = (maxPage > 0 ? (state.getCurrentPage() + 1) : 0) + "/" + maxPage;
        gui.drawString(font, pageInfo, gridLeft + gridWidth / 2 - font.width(pageInfo) / 2, topPos + imageHeight - 110, 0xFFFFFF, false);

        int catX = leftPos + 7;
        int catY = topPos + imageHeight - 100;
        for (FluxCategory cat : FluxCategoryManager.getVisibleCategories()) {
            String label = cat.displayName().getString();
            int btnW = font.width(label) + 6;
            if (catX + btnW > leftPos + imageWidth - 10) break;

            boolean active = cat.id().equals(state.getSelectedCategory());
            int bg = active ? 0xFF4444FF : 0xFF333333;
            gui.fill(catX, catY, catX + btnW, catY + 12, bg);
            gui.drawString(font, label, catX + 3, catY + 2, active ? 0xFFFFFF : 0xAAAAAA, false);
            catX += btnW + 2;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics gui, int mouseX, int mouseY) {
        gui.drawString(font, title, titleLabelX, titleLabelY, 0xFFFFFF, false);
        gui.drawString(font, playerInventoryTitle, inventoryLabelX, inventoryLabelY, 0x666666, false);

        gui.drawString(font, Component.translatable("gui.onixflux.learn_slot"), 140, 20, 0x888888, false);
        gui.drawString(font, Component.translatable("gui.onixflux.output_slot"), 140, 56, 0x888888, false);
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        super.render(gui, mouseX, mouseY, partialTick);

        int learnBtnX = leftPos + 140;
        int learnBtnY = topPos + 70;
        int btnW = 70;
        int btnH = 16;

        boolean hoverLearn = mouseX >= learnBtnX && mouseX <= learnBtnX + btnW && mouseY >= learnBtnY && mouseY <= learnBtnY + btnH;
        gui.fill(learnBtnX, learnBtnY, learnBtnX + btnW, learnBtnY + btnH, hoverLearn ? 0xFF448844 : 0xFF335533);
        gui.drawString(font, Component.translatable("gui.onixflux.learn"), learnBtnX + 5, learnBtnY + 4, 0xFFFFFF, false);

        int transmBtnX = leftPos + 140;
        int transmBtnY = topPos + 88;
        boolean hoverTransm = mouseX >= transmBtnX && mouseX <= transmBtnX + btnW && mouseY >= transmBtnY && mouseY <= transmBtnY + btnH;
        gui.fill(transmBtnX, transmBtnY, transmBtnX + btnW, transmBtnY + btnH, hoverTransm ? 0xFF444488 : 0xFF333366);
        gui.drawString(font, Component.translatable("gui.onixflux.transmute"), transmBtnX + 5, transmBtnY + 4, 0xFFFFFF, false);

        renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            int gridLeft = leftPos + 7;
            int gridTop = topPos + 44;

            for (int i = 0; i < pageEntries.size(); i++) {
                int col = i % GRID_COLS;
                int row = i / GRID_COLS;
                int x = gridLeft + col * 18;
                int y = gridTop + row * 18 - state.getScrollOffset();

                if (mx >= x && mx < x + 18 && my >= y && my < y + 18) {
                    state.setSelectedItem(pageEntries.get(i).item());
                    return true;
                }
            }

            int learnBtnX = leftPos + 140;
            int learnBtnY = topPos + 70;
            int btnW = 70;
            int btnH = 16;
            if (mx >= learnBtnX && mx < learnBtnX + btnW && my >= learnBtnY && my < learnBtnY + btnH) {
                handleLearn();
                return true;
            }

            int transmBtnX = leftPos + 140;
            int transmBtnY = topPos + 88;
            if (mx >= transmBtnX && mx < transmBtnX + btnW && my >= transmBtnY && my < transmBtnY + btnH) {
                handleTransmute();
                return true;
            }

            int catY = topPos + imageHeight - 100;
            int catX = leftPos + 7;
            for (FluxCategory cat : FluxCategoryManager.getVisibleCategories()) {
                String label = cat.displayName().getString();
                int btnW2 = font.width(label) + 6;
                if (mx >= catX && mx < catX + btnW2 && my >= catY && my < catY + 12) {
                    state.setSelectedCategory(cat.id());
                    refreshEntries();
                    return true;
                }
                catX += btnW2 + 2;
            }
        }

        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (searchBox.isFocused()) {
            String before = searchBox.getValue();
            boolean handled = searchBox.keyPressed(key, scanCode, modifiers);
            if (handled) {
                String after = searchBox.getValue();
                if (!after.equals(before)) {
                    state.setSearchQuery(after);
                    refreshEntries();
                }
                return true;
            }
            if (key == 256) {
                searchBox.setFocused(false);
                return true;
            }
            return true;
        }
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (searchBox.isFocused()) {
            String before = searchBox.getValue();
            boolean handled = searchBox.charTyped(codePoint, modifiers);
            if (handled) {
                String after = searchBox.getValue();
                if (!after.equals(before)) {
                    state.setSearchQuery(after);
                    refreshEntries();
                }
                return true;
            }
            return true;
        }
        return super.charTyped(codePoint, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        int gridLeft = leftPos + 7;
        int gridTop = topPos + 44;
        int gridWidth = GRID_COLS * 18;
        int gridHeight = GRID_ROWS * 18;

        if (mx >= gridLeft && mx < gridLeft + gridWidth && my >= gridTop && my < gridTop + gridHeight) {
            int newScroll = state.getScrollOffset() - (int) (delta * 18);
            int maxScroll = Math.max(0, (pageEntries.size() + GRID_COLS - 1) / GRID_COLS * 18 - gridHeight);
            state.setScrollOffset(Math.max(0, Math.min(newScroll, maxScroll)));
            return true;
        }
        return super.mouseScrolled(mx, my, delta);
    }

    private void handleLearn() {
        var mc = Minecraft.getInstance();
        if (mc.player == null || mc.gameMode == null) return;

        var stack = menu.getBlockEntity().getInventory().getStackInSlot(0);
        if (stack.isEmpty()) return;

        ClientDispatcher.sendToServer(new LearnItemPacket(menu.containerId));
        refreshEntries();
    }

    private void handleTransmute() {
        Item selected = state.getSelectedItem();
        if (selected == null) return;

        ClientDispatcher.sendToServer(new CreateItemPacket(menu.containerId, selected, 1));
        refreshEntries();
    }

}
