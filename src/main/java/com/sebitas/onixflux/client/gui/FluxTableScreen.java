package com.sebitas.onixflux.client.gui;

import com.sebitas.onixflux.client.gui.animation.FadeAnimation;
import com.sebitas.onixflux.client.gui.render.*;
import com.sebitas.onixflux.client.gui.widget.*;
import com.sebitas.onixflux.transmutation.FluxCategoryManager;
import com.sebitas.onixflux.transmutation.FluxCategoryManager.FluxCategory;
import com.sebitas.onixflux.transmutation.FluxItemEntry;
import com.sebitas.onixflux.transmutation.FluxTableMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FluxTableScreen extends AbstractContainerScreen<FluxTableMenu> {

    private FluxTableScreenHandler handler;
    private FadeAnimation openAnimation;

    private SearchWidget searchWidget;
    private CategoryWidget categoryWidget;
    private ItemGridWidget gridWidget;
    private ItemInfoWidget infoWidget;
    private FxBarWidget fxBarWidget;
    private PageWidget pageWidget;
    private FooterWidget footerWidget;
    private HeaderWidget headerWidget;
    private NotificationWidget notificationWidget;
    private TooltipWidget tooltipWidget;
    private LearnSlotWidget learnSlotWidget;

    public FluxTableScreen(FluxTableMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 320;
        this.imageHeight = 240;
        this.openAnimation = new FadeAnimation(15, 0.0f, 1.0f);
        this.handler = new FluxTableScreenHandler(menu, this::scheduleRefresh);
    }

    @Override
    protected void init() {
        super.init();
        openAnimation.start();

        int left = leftPos;
        int top = topPos;

        headerWidget = new HeaderWidget(left + 4, top + 4, imageWidth - 8, 12, "Flux Table");
        searchWidget = new SearchWidget(left + 4, top + 20, imageWidth - 8, this::onSearch);
        fxBarWidget = new FxBarWidget(left + 4, top + 38, imageWidth - 8, 12);

        int mainY = top + 54;
        int mainH = imageHeight - 100;

        int gridW = 180;
        int gridH = mainH - 4;
        int gridX = left + 4;
        int gridY = mainY + 2;
        gridWidget = new ItemGridWidget(gridX, gridY, gridW, gridH, this::onSelect, this::onDoubleClick);

        int sideX = gridX + gridW + 6;
        int sideW = imageWidth - gridW - 14;
        infoWidget = new ItemInfoWidget(sideX, mainY, sideW, 54);

        int catX = left + 4;
        int catY = top + imageHeight - 42;
        var categories = new ArrayList<String>();
        categories.add("All");
        for (FluxCategory cat : FluxCategoryManager.getVisibleCategories()) {
            categories.add(cat.displayName().getString());
        }
        categoryWidget = new CategoryWidget(catX, catY, imageWidth - 8, categories, this::onCategorySelect);

        pageWidget = new PageWidget(left + 4, top + imageHeight - 20, 60, 12, this::onPrevPage, this::onNextPage);
        footerWidget = new FooterWidget(left + 68, top + imageHeight - 20, imageWidth - 76, 12);
        notificationWidget = new NotificationWidget();
        tooltipWidget = new TooltipWidget();
        learnSlotWidget = new LearnSlotWidget(sideX, top + 110, 18);

        handler.refreshEntries();
    }

    @Override
    public void resize(Minecraft mc, int w, int h) {
        String query = searchWidget.value();
        super.resize(mc, w, h);
        searchWidget.clear();
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        renderBackground(gui);
        BackgroundRenderer.renderFull(gui, width, height);

        gui.pose().pushPose();
        if (openAnimation.isRunning()) {
            float alpha = openAnimation.alpha();
            int a = (int) (alpha * 255);
            gui.pose().translate(0, 0, 0);
        }

        headerWidget.render(gui, mouseX, mouseY, partialTick);
        searchWidget.render(gui, mouseX, mouseY, partialTick);
        fxBarWidget.setFx(handler.playerFx(), handler.maxFx());
        fxBarWidget.render(gui, mouseX, mouseY, partialTick);
        gridWidget.render(gui, mouseX, mouseY, partialTick);

        // Info panel
        var selected = handler.state().getSelectedItem();
        if (selected != null) {
            var mc = Minecraft.getInstance();
            gui.renderItem(new ItemStack(selected), leftPos + imageWidth - 22, topPos + 56);
            infoWidget.setDisplayName(new ItemStack(selected).getHoverName().getString());
            infoWidget.setFxValue(handler.getFxValue(selected));
            var mcPlayer = mc.player;
            if (mcPlayer != null) {
                infoWidget.setModName(selected.getDescriptionId().split("\\.")[1]);
            }
            infoWidget.render(gui, mouseX, mouseY, partialTick);
        }

        // Learn slot indicator
        var inv = menu.getBlockEntity().getInventory();
        boolean hasLearnStack = !inv.getStackInSlot(0).isEmpty();
        learnSlotWidget.setHasStack(hasLearnStack);
        learnSlotWidget.render(gui, mouseX, mouseY, partialTick);

        categoryWidget.render(gui, mouseX, mouseY, partialTick);
        pageWidget.setPage(handler.currentPage(), handler.maxPage());
        pageWidget.render(gui, mouseX, mouseY, partialTick);
        footerWidget.render(gui, mouseX, mouseY, partialTick);
        notificationWidget.render(gui, width / 2, topPos + 50);
        tooltipWidget.render(gui, mouseX, mouseY);

        gui.pose().popPose();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        super.render(gui, mouseX, mouseY, partialTick);
        renderTooltip(gui, mouseX, mouseY);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        openAnimation.tick();
        notificationWidget.tick();
        handler.refreshEntries();
    }

    @Override
    public boolean mouseClicked(double mx, double my, int button) {
        if (button == 0) {
            if (searchWidget.mouseClicked(mx, my, button)) return true;
            if (gridWidget.mouseClicked(mx, my, button)) {
                boolean shift = hasShiftDown();
                boolean ctrl = hasControlDown();
                handler.handleExtract(gridWidget.selectedIndex(), shift, ctrl);
                return true;
            }
            if (categoryWidget.mouseClicked(mx, my, button)) return true;
            if (pageWidget.mouseClicked(mx, my, button)) return true;

            // Learn button
            int learnX = leftPos + imageWidth - 74;
            int learnY = topPos + 110;
            if (mx >= learnX && mx <= learnX + 70 && my >= learnY && my <= learnY + 16) {
                handler.handleLearn();
                return true;
            }
        }
        return super.mouseClicked(mx, my, button);
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double delta) {
        if (gridWidget.mouseScrolled(mx, my, delta)) return true;
        return super.mouseScrolled(mx, my, delta);
    }

    @Override
    public boolean keyPressed(int key, int scanCode, int modifiers) {
        if (searchWidget.keyPressed(key, scanCode, modifiers)) return true;
        return super.keyPressed(key, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        if (searchWidget.charTyped(codePoint, modifiers)) return true;
        return super.charTyped(codePoint, modifiers);
    }

    private void onSearch(String query) {
        handler.setSearchQuery(query);
        gridWidget.updateEntries(handler.getCurrentPageEntries());
    }

    private void onSelect(int index) {
        var entry = gridWidget.getEntry(index);
        if (entry != null) {
            handler.state().setSelectedItem(entry.item());
        }
    }

    private void onDoubleClick(int index) {
        handler.handleExtract(index, false, true);
    }

    private void onCategorySelect(int index) {
        if (index == 0) {
            handler.setCategory("");
        } else {
            var cats = FluxCategoryManager.getVisibleCategories();
            if (index - 1 < cats.size()) {
                handler.setCategory(cats.get(index - 1).id());
            }
        }
        gridWidget.updateEntries(handler.getCurrentPageEntries());
    }

    private void onPrevPage() {
        handler.prevPage();
        gridWidget.updateEntries(handler.getCurrentPageEntries());
    }

    private void onNextPage() {
        handler.nextPage();
        gridWidget.updateEntries(handler.getCurrentPageEntries());
    }

    private void scheduleRefresh(Runnable r) {
        gridWidget.updateEntries(handler.getCurrentPageEntries());
        fxBarWidget.setFx(handler.playerFx(), handler.maxFx());
    }

}
