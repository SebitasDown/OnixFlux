package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluxItemModelProvider extends ItemModelProvider {

    public FluxItemModelProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OnixFlux.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("flux_table", modLoc("block/flux_table"));
    }

}
