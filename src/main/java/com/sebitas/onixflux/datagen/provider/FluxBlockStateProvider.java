package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FluxBlockStateProvider extends BlockStateProvider {

    public FluxBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, OnixFlux.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.FLUX_TABLE.get(), models().cube("flux_table",
                modLoc("block/flux_table_side"),
                modLoc("block/flux_table_side"),
                modLoc("block/flux_table_side"),
                modLoc("block/flux_table_side"),
                modLoc("block/flux_table_side"),
                modLoc("block/flux_table_side")
        ).texture("particle", modLoc("block/flux_table_side")));
    }

}
