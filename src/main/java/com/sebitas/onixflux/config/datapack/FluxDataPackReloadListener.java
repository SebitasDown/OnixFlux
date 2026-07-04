package com.sebitas.onixflux.config.datapack;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.config.FluxConfigDiagnostics;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FluxDataPackReloadListener extends SimplePreparableReloadListener<List<FluxValueJsonEntry>> {

    private static final Logger LOGGER = LoggerFactory.getLogger("FluxDataPack");
    private static final String PATH = "onixflux/fx";

    @Override
    protected List<FluxValueJsonEntry> prepare(ResourceManager manager, ProfilerFiller profiler) {
        List<FluxValueJsonEntry> allEntries = new ArrayList<>();
        Map<ResourceLocation, Resource> resources = manager.listResources(PATH, loc -> loc.getPath().endsWith(".json"));
        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            ResourceLocation fileId = entry.getKey();
            try (InputStream is = entry.getValue().open();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) content.append(line).append('\n');
                List<FluxValueJsonEntry> parsed = FluxValueJsonFormat.parse(content.toString());
                allEntries.addAll(parsed);
                LOGGER.debug("Loaded {} FX entries from {}", parsed.size(), fileId);
            } catch (Exception e) {
                FluxConfigDiagnostics.recordError();
                LOGGER.warn("Failed to load FX data pack file {}: {}", fileId, e.getMessage());
            }
        }
        return allEntries;
    }

    @Override
    protected void apply(List<FluxValueJsonEntry> entries, ResourceManager manager, ProfilerFiller profiler) {
        int registered = 0;
        int skipped = 0;
        for (FluxValueJsonEntry entry : entries) {
            if (!entry.isValid()) { skipped++; continue; }
            if (entry.hasItem()) {
                ResourceLocation id = ResourceLocation.tryParse(entry.item());
                if (id == null) { skipped++; continue; }
                Item item = BuiltInRegistries.ITEM.get(id);
                if (item == null || item == Items.AIR) { skipped++; continue; }
                FluxEngine.register(item, entry.fx(), FluxSource.DATAPACK);
                registered++;
            } else if (entry.hasTag()) {
                String tagStr = entry.tag().startsWith("#") ? entry.tag().substring(1) : entry.tag();
                ResourceLocation tagId = ResourceLocation.tryParse(tagStr);
                if (tagId == null) { skipped++; continue; }
                TagKey<Item> tagKey = TagKey.create(BuiltInRegistries.ITEM.key(), tagId);
                for (Item item : BuiltInRegistries.ITEM) {
                    if (item.builtInRegistryHolder().is(tagKey)) {
                        if (!FluxEngine.hasValue(item)) {
                            FluxEngine.register(item, entry.fx(), FluxSource.DATAPACK);
                            registered++;
                        }
                    }
                }
            }
        }
        LOGGER.info("Data pack FX: {} registered, {} skipped", registered, skipped);
    }

}
