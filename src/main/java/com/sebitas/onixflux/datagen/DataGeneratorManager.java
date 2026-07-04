package com.sebitas.onixflux.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class DataGeneratorManager {

    private final DataGenerator generator;
    private final PackOutput packOutput;
    private final ExistingFileHelper existingFileHelper;

    public DataGeneratorManager(DataGenerator generator, PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        this.generator = generator;
        this.packOutput = packOutput;
        this.existingFileHelper = existingFileHelper;
    }

    public void registerAll() {
    }

    public DataGenerator generator() {
        return generator;
    }

    public PackOutput packOutput() {
        return packOutput;
    }

    public ExistingFileHelper existingFileHelper() {
        return existingFileHelper;
    }

}
