package com.sebitas.onixflux.config.datapack;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public final class FluxValueJsonFormat {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private FluxValueJsonFormat() {}

    public static List<FluxValueJsonEntry> parse(String json) {
        List<FluxValueJsonEntry> entries = new ArrayList<>();
        JsonElement root = GSON.fromJson(json, JsonElement.class);
        if (root == null) return entries;

        if (root.isJsonObject()) {
            FluxValueJsonEntry entry = parseObject(root.getAsJsonObject());
            if (entry != null) entries.add(entry);
        } else if (root.isJsonArray()) {
            for (JsonElement elem : root.getAsJsonArray()) {
                if (elem.isJsonObject()) {
                    FluxValueJsonEntry entry = parseObject(elem.getAsJsonObject());
                    if (entry != null) entries.add(entry);
                }
            }
        }
        return entries;
    }

    private static FluxValueJsonEntry parseObject(JsonObject obj) {
        String item = obj.has("item") ? obj.get("item").getAsString() : null;
        String tag = obj.has("tag") ? obj.get("tag").getAsString() : null;
        long fx = obj.has("fx") ? obj.get("fx").getAsLong() : 0;
        if (fx <= 0) return null;
        if (item == null && tag == null) return null;
        if (item != null && ResourceLocation.tryParse(item) == null) return null;
        return new FluxValueJsonEntry(item, tag, fx);
    }

    public static String toJson(List<FluxValueJsonEntry> entries) {
        JsonArray arr = new JsonArray();
        for (FluxValueJsonEntry entry : entries) {
            JsonObject obj = new JsonObject();
            if (entry.hasItem()) obj.addProperty("item", entry.item());
            if (entry.hasTag()) obj.addProperty("tag", entry.tag());
            obj.addProperty("fx", entry.fx());
            arr.add(obj);
        }
        return GSON.toJson(arr);
    }

}
