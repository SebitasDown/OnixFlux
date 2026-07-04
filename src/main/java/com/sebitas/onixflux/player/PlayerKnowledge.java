package com.sebitas.onixflux.player;

import net.minecraft.world.item.Item;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class PlayerKnowledge {

    private final Set<Item> learned;

    public PlayerKnowledge() {
        this.learned = new HashSet<>();
    }

    public PlayerKnowledge(Set<Item> initial) {
        this.learned = new HashSet<>(initial);
    }

    public boolean learn(Item item) {
        PlayerValidation.checkItem(item);
        return learned.add(item);
    }

    public boolean forget(Item item) {
        PlayerValidation.checkItem(item);
        return learned.remove(item);
    }

    public boolean knows(Item item) {
        PlayerValidation.checkItem(item);
        return learned.contains(item);
    }

    public Set<Item> getLearned() {
        return Collections.unmodifiableSet(learned);
    }

    public int count() {
        return learned.size();
    }

    public void clear() {
        learned.clear();
    }

}
