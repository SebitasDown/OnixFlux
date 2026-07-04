package com.sebitas.onixflux.datagen;

import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.datagen.provider.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OnixFlux.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DatagenEntrypoint {

    private DatagenEntrypoint() {}

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new FluxBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FluxItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new FluxLanguageProvider(packOutput));

        var tagProvider = new FluxTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), tagProvider);

        generator.addProvider(event.includeServer(), new FluxLootTableProvider(packOutput));
        generator.addProvider(event.includeServer(), new FluxRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), new FluxAdvancementProvider(packOutput, lookupProvider, existingFileHelper));

        var dataGeneratorManager = new DataGeneratorManager(generator, packOutput, existingFileHelper);
        dataGeneratorManager.registerAll();
    }

}
