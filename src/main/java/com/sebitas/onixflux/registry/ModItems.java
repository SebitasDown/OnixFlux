package com.sebitas.onixflux.registry;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, OnixFlux.MOD_ID);

    public static final RegistryObject<Item> FLUX_TABLE_ITEM = ITEMS.register("flux_table",
            () -> new BlockItem(ModBlocks.FLUX_TABLE.get(), new Item.Properties()));

    private ModItems() {}

}
