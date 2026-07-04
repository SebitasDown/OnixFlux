package com.sebitas.onixflux.api;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import java.util.Set;

public interface FluxPlayerService {

    long getFlux(Player player);

    void setFlux(Player player, long value);

    boolean addFlux(Player player, long amount);

    boolean removeFlux(Player player, long amount);

    boolean learn(Player player, Item item);

    boolean forget(Player player, Item item);

    boolean knows(Player player, Item item);

    Set<Item> getKnowledge(Player player);

    int learnedCount(Player player);

    boolean hasFlux(Player player);

}
