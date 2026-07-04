package com.sebitas.onixflux.fx.provider;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class VanillaProvider implements FluxValueProvider {

    @Override
    public void provide() {
        register(Items.STONE, 1);
        register(Items.COBBLESTONE, 1);
        register(Items.DIRT, 1);
        register(Items.GRAVEL, 1);
        register(Items.SAND, 1);
        register(Items.END_STONE, 1);
        register(Items.COBBLESTONE_WALL, 1);
        register(Items.STICK, 4);
        register(Items.FLINT, 4);
        register(Items.OAK_PLANKS, 8);
        register(Items.SPRUCE_PLANKS, 8);
        register(Items.BIRCH_PLANKS, 8);
        register(Items.JUNGLE_PLANKS, 8);
        register(Items.ACACIA_PLANKS, 8);
        register(Items.DARK_OAK_PLANKS, 8);
        register(Items.MANGROVE_PLANKS, 8);
        register(Items.CLAY_BALL, 16);
        register(Items.BRICK, 16);
        register(Items.NETHER_BRICK, 16);
        register(Items.OAK_LOG, 32);
        register(Items.REDSTONE, 64);
        register(Items.COAL, 128);
        register(Items.CHARCOAL, 128);
        register(Items.IRON_INGOT, 256);
        register(Items.QUARTZ, 256);
        register(Items.PRISMARINE_SHARD, 256);
        register(Items.IRON_NUGGET, 28);
        register(Items.LAPIS_LAZULI, 864);
        register(Items.PRISMARINE_CRYSTALS, 1024);
        register(Items.GOLD_INGOT, 2048);
        register(Items.GOLD_NUGGET, 227);
        register(Items.OBSIDIAN, 4096);
        register(Items.DIAMOND, 8192);
        register(Items.EMERALD, 16384);
        register(Items.NETHERITE_INGOT, 139264);
    }

    private static void register(Item item, long value) {
        FluxEngine.register(item, value, FluxSource.DEFAULT);
    }

}
