package com.sebitas.onixflux.api.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;

public class FluxPlayerForgetEvent extends Event {

    private final Player player;
    private final Item item;

    public FluxPlayerForgetEvent(Player player, Item item) {
        this.player = player;
        this.item = item;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getItem() {
        return item;
    }

}
