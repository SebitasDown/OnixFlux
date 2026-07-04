package com.sebitas.onixflux.registry;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.transmutation.FluxTableBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, OnixFlux.MOD_ID);

    public static final RegistryObject<Block> FLUX_TABLE = BLOCKS.register("flux_table", FluxTableBlock::new);

    private ModBlocks() {}

}
