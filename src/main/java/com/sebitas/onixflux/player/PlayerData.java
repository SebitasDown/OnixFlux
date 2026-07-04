package com.sebitas.onixflux.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class PlayerData implements PlayerCapability {

    private static final int CURRENT_VERSION = 1;
    private static final String TAG_VERSION = "DataVersion";
    private static final String TAG_FLUX = "Flux";
    private static final String TAG_LEARNED = "LearnedItems";

    private final PlayerFlux flux;
    private final PlayerKnowledge knowledge;

    public PlayerData() {
        this.flux = new PlayerFlux();
        this.knowledge = new PlayerKnowledge();
    }

    @Override
    public long getFlux() {
        return flux.get();
    }

    @Override
    public void setFlux(long value) {
        flux.set(value);
    }

    @Override
    public boolean addFlux(long value) {
        return flux.add(value);
    }

    @Override
    public boolean removeFlux(long value) {
        return flux.remove(value);
    }

    @Override
    public boolean hasFlux() {
        return flux.has();
    }

    @Override
    public void clearFlux() {
        flux.clear();
    }

    @Override
    public boolean learn(Item item) {
        return knowledge.learn(item);
    }

    @Override
    public boolean forget(Item item) {
        return knowledge.forget(item);
    }

    @Override
    public boolean knows(Item item) {
        return knowledge.knows(item);
    }

    @Override
    public Set<Item> getLearnedItems() {
        return knowledge.getLearned();
    }

    @Override
    public int learnedCount() {
        return knowledge.count();
    }

    @Override
    public void clearKnowledge() {
        knowledge.clear();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt(TAG_VERSION, CURRENT_VERSION);
        tag.putLong(TAG_FLUX, flux.get());

        ListTag learnedList = new ListTag();
        for (Item item : knowledge.getLearned()) {
            ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
            if (id != null) {
                learnedList.add(StringTag.valueOf(id.toString()));
            }
        }
        tag.put(TAG_LEARNED, learnedList);

        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        flux.set(tag.getLong(TAG_FLUX));

        knowledge.clear();
        ListTag learnedList = tag.getList(TAG_LEARNED, Tag.TAG_STRING);
        for (int i = 0; i < learnedList.size(); i++) {
            String itemId = learnedList.getString(i);
            ResourceLocation id = ResourceLocation.tryParse(itemId);
            if (id != null) {
                Item item = ForgeRegistries.ITEMS.getValue(id);
                if (item != null) {
                    knowledge.learn(item);
                }
            }
        }
    }

    public PlayerData copy() {
        PlayerData copy = new PlayerData();
        copy.flux.set(this.flux.get());
        for (Item item : this.knowledge.getLearned()) {
            copy.knowledge.learn(item);
        }
        return copy;
    }

}
