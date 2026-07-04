package com.sebitas.onixflux.player;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class PlayerDataManager {

    private PlayerDataManager() {
    }

    public static PlayerCapability get(Player player) {
        return player.getCapability(PlayerCapabilityAttacher.PLAYER_DATA)
                .orElseThrow(() -> new PlayerException("Player capability not available for " + player.getName().getString()));
    }

    public static long getFlux(Player player) {
        return get(player).getFlux();
    }

    public static void setFlux(Player player, long value) {
        PlayerCapability data = get(player);
        data.setFlux(value);
        PlayerDataEvents.fireFluxChanged(player, value);
        PlayerSyncManager.syncFlux(player);
    }

    public static boolean addFlux(Player player, long value) {
        PlayerCapability data = get(player);
        boolean result = data.addFlux(value);
        if (result) {
            PlayerDataEvents.fireFluxChanged(player, data.getFlux());
            PlayerSyncManager.syncFlux(player);
        }
        return result;
    }

    public static boolean removeFlux(Player player, long value) {
        PlayerCapability data = get(player);
        boolean result = data.removeFlux(value);
        if (result) {
            PlayerDataEvents.fireFluxChanged(player, data.getFlux());
            PlayerSyncManager.syncFlux(player);
        }
        return result;
    }

    public static boolean hasFlux(Player player) {
        return get(player).hasFlux();
    }

    public static boolean learn(Player player, Item item) {
        PlayerCapability data = get(player);
        boolean result = data.learn(item);
        if (result) {
            PlayerDataEvents.fireItemLearned(player, item);
            PlayerSyncManager.syncKnowledge(player);
        }
        return result;
    }

    public static boolean forget(Player player, Item item) {
        PlayerCapability data = get(player);
        boolean result = data.forget(item);
        if (result) {
            PlayerDataEvents.fireItemForgotten(player, item);
            PlayerSyncManager.syncKnowledge(player);
        }
        return result;
    }

    public static boolean knows(Player player, Item item) {
        return get(player).knows(item);
    }

    public static Set<Item> getLearnedItems(Player player) {
        return get(player).getLearnedItems();
    }

    public static int learnedCount(Player player) {
        return get(player).learnedCount();
    }

    public static void clearLearned(Player player) {
        PlayerCapability data = get(player);
        Set<Item> items = Set.copyOf(data.getLearnedItems());
        for (Item item : items) {
            data.forget(item);
        }
        PlayerSyncManager.syncKnowledge(player);
    }

}
