package com.sebitas.onixflux.registry;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, OnixFlux.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ONIXFLUX_TAB = TABS.register("onixflux",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.FLUX_TABLE_ITEM.get()))
                    .title(Component.translatable("itemGroup.onixflux"))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.FLUX_TABLE_ITEM.get());
                    })
                    .build());

    private ModCreativeTabs() {}

}
