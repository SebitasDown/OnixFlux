package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FluxLootTableProvider extends LootTableProvider {

    public FluxLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(FluxBlockLoot::new, LootContextParamSets.BLOCK)));
    }

    private static class FluxBlockLoot extends BlockLootSubProvider {

        protected FluxBlockLoot() {
            super(Set.of(), FeatureFlags.REGISTRY.allFlags());
        }

        @Override
        protected void generate() {
            dropSelf(ModBlocks.FLUX_TABLE.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return ModBlocks.BLOCKS.getEntries().stream()
                    .map(entry -> (Block) entry.get())
                    .collect(Collectors.toList());
        }

    }

}
