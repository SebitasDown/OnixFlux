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

        var blockStateProvider = new FluxBlockStateProvider(packOutput, existingFileHelper);
        generator.addProvider(event.includeClient(), blockStateProvider);

        var itemModelProvider = new FluxItemModelProvider(packOutput, existingFileHelper);
        generator.addProvider(event.includeClient(), itemModelProvider);

        var languageProvider = new FluxLanguageProvider(packOutput);
        generator.addProvider(event.includeClient(), languageProvider);

        var lootTableProvider = new FluxLootTableProvider(packOutput);
        generator.addProvider(event.includeServer(), lootTableProvider);

        var recipeProvider = new FluxRecipeProvider(packOutput);
        generator.addProvider(event.includeServer(), recipeProvider);

        var tagProvider = new FluxTagProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), tagProvider);

        var itemTagProvider = new FluxItemTagProvider(packOutput, lookupProvider, tagProvider.contentsGetter(), existingFileHelper);
        generator.addProvider(event.includeServer(), itemTagProvider);

        var advancementProvider = new FluxAdvancementProvider(packOutput, lookupProvider, existingFileHelper);
        generator.addProvider(event.includeServer(), advancementProvider);

        var dataGeneratorManager = new DataGeneratorManager(generator, packOutput, existingFileHelper);
        dataGeneratorManager.registerAll();
    }

}
