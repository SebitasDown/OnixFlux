package com.sebitas.onixflux.integration.adapter;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Optional;

public interface PlayerAdapter {

    default Optional<Long> getFlux(Player player) {
        return Optional.empty();
    }

    default boolean addFlux(Player player, long amount) {
        return false;
    }

    default boolean removeFlux(Player player, long amount) {
        return false;
    }

    default boolean setFlux(Player player, long amount) {
        return false;
    }

    default boolean learnItem(Player player, Item item) {
        return false;
    }

    default boolean forgetItem(Player player, Item item) {
        return false;
    }

    default boolean knowsItem(Player player, Item item) {
        return false;
    }

    default boolean handles(Player player) {
        return true;
    }

}
