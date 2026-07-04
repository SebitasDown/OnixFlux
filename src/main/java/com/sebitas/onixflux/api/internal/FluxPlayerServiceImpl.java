package com.sebitas.onixflux.api.internal;

import com.sebitas.onixflux.api.FluxApiConstants;
import com.sebitas.onixflux.api.FluxApiValidation;
import com.sebitas.onixflux.api.FluxPlayerService;
import com.sebitas.onixflux.api.event.FluxPlayerFluxChangedEvent;
import com.sebitas.onixflux.api.event.FluxPlayerForgetEvent;
import com.sebitas.onixflux.api.event.FluxPlayerLearnEvent;
import com.sebitas.onixflux.player.PlayerDataManager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.MinecraftForge;

import java.util.Set;

public final class FluxPlayerServiceImpl implements FluxPlayerService {

    @Override
    public long getFlux(Player player) {
        FluxApiValidation.checkPlayer(player);
        return PlayerDataManager.getFlux(player);
    }

    @Override
    public void setFlux(Player player, long value) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkNonNegative(value);
        FluxApiValidation.checkNotOverflow(0, value);

        long old = PlayerDataManager.getFlux(player);
        PlayerDataManager.setFlux(player, value);
        MinecraftForge.EVENT_BUS.post(new FluxPlayerFluxChangedEvent(player, old, value));
    }

    @Override
    public boolean addFlux(Player player, long amount) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkPositive(amount);

        long current = PlayerDataManager.getFlux(player);
        FluxApiValidation.checkNotOverflow(current, amount);

        boolean result = PlayerDataManager.addFlux(player, amount);
        if (result) {
            long newVal = PlayerDataManager.getFlux(player);
            MinecraftForge.EVENT_BUS.post(new FluxPlayerFluxChangedEvent(player, current, newVal));
        }
        return result;
    }

    @Override
    public boolean removeFlux(Player player, long amount) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkPositive(amount);

        long current = PlayerDataManager.getFlux(player);
        FluxApiValidation.checkNotOverflowSubtract(current, amount);

        boolean result = PlayerDataManager.removeFlux(player, amount);
        if (result) {
            long newVal = PlayerDataManager.getFlux(player);
            MinecraftForge.EVENT_BUS.post(new FluxPlayerFluxChangedEvent(player, current, newVal));
        }
        return result;
    }

    @Override
    public boolean learn(Player player, Item item) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkItem(item);

        boolean result = PlayerDataManager.learn(player, item);
        if (result) {
            MinecraftForge.EVENT_BUS.post(new FluxPlayerLearnEvent(player, item));
        }
        return result;
    }

    @Override
    public boolean forget(Player player, Item item) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkItem(item);

        boolean result = PlayerDataManager.forget(player, item);
        if (result) {
            MinecraftForge.EVENT_BUS.post(new FluxPlayerForgetEvent(player, item));
        }
        return result;
    }

    @Override
    public boolean knows(Player player, Item item) {
        FluxApiValidation.checkPlayer(player);
        FluxApiValidation.checkItem(item);
        return PlayerDataManager.knows(player, item);
    }

    @Override
    public Set<Item> getKnowledge(Player player) {
        FluxApiValidation.checkPlayer(player);
        return PlayerDataManager.getLearnedItems(player);
    }

    @Override
    public int learnedCount(Player player) {
        FluxApiValidation.checkPlayer(player);
        return PlayerDataManager.learnedCount(player);
    }

    @Override
    public boolean hasFlux(Player player) {
        FluxApiValidation.checkPlayer(player);
        return PlayerDataManager.hasFlux(player);
    }

}
