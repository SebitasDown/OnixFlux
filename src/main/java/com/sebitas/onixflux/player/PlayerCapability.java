package com.sebitas.onixflux.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;

import java.util.Set;

public interface PlayerCapability {

    long getFlux();

    void setFlux(long value);

    boolean addFlux(long value);

    boolean removeFlux(long value);

    boolean hasFlux();

    void clearFlux();

    boolean learn(Item item);

    boolean forget(Item item);

    boolean knows(Item item);

    Set<Item> getLearnedItems();

    int learnedCount();

    void clearKnowledge();

    CompoundTag serializeNBT();

    void deserializeNBT(CompoundTag tag);

}
