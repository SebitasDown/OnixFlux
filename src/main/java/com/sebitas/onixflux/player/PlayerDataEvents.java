package com.sebitas.onixflux.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class PlayerDataEvents {

    private PlayerDataEvents() {
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            PlayerSyncManager.syncAll(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            PlayerSyncManager.syncAll(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide) {
            PlayerSyncManager.syncAll(player);
        }
    }

    public static void fireFluxChanged(Player player, long newFlux) {
        MinecraftForge.EVENT_BUS.post(new PlayerFluxChangedEvent(player, newFlux));
    }

    public static void fireItemLearned(Player player, Item item) {
        MinecraftForge.EVENT_BUS.post(new PlayerLearnItemEvent(player, item));
    }

    public static void fireItemForgotten(Player player, Item item) {
        MinecraftForge.EVENT_BUS.post(new PlayerForgetItemEvent(player, item));
    }

    public static void fireDataLoaded(Player player) {
        MinecraftForge.EVENT_BUS.post(new PlayerDataLoadedEvent(player));
    }

    public static void fireDataSaved(Player player) {
        MinecraftForge.EVENT_BUS.post(new PlayerDataSavedEvent(player));
    }

    public static class PlayerFluxChangedEvent extends PlayerEvent {
        private final long newFlux;
        public PlayerFluxChangedEvent(Player player, long newFlux) {
            super(player);
            this.newFlux = newFlux;
        }
        public long getNewFlux() { return newFlux; }
    }

    public static class PlayerLearnItemEvent extends PlayerEvent {
        private final Item item;
        public PlayerLearnItemEvent(Player player, Item item) {
            super(player);
            this.item = item;
        }
        public Item getItem() { return item; }
    }

    public static class PlayerForgetItemEvent extends PlayerEvent {
        private final Item item;
        public PlayerForgetItemEvent(Player player, Item item) {
            super(player);
            this.item = item;
        }
        public Item getItem() { return item; }
    }

    public static class PlayerDataLoadedEvent extends PlayerEvent {
        public PlayerDataLoadedEvent(Player player) { super(player); }
    }

    public static class PlayerDataSavedEvent extends PlayerEvent {
        public PlayerDataSavedEvent(Player player) { super(player); }
    }

}
