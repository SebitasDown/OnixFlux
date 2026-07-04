package com.sebitas.onixflux.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public class PlayerDataStorage implements INBTSerializable<CompoundTag> {

    private final PlayerData data;

    public PlayerDataStorage() {
        this.data = new PlayerData();
    }

    public PlayerData getData() {
        return data;
    }

    @Override
    public CompoundTag serializeNBT() {
        return data.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        data.deserializeNBT(tag);
    }

}
