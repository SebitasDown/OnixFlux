package com.sebitas.onixflux.config;

import com.google.gson.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public final class ConfigSerializer {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    private ConfigSerializer() {}

    public static String toJson(Map<ResourceLocation, Long> values) {
        JsonObject root = new JsonObject();
        JsonArray entries = new JsonArray();
        for (Map.Entry<ResourceLocation, Long> entry : values.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("item", entry.getKey().toString());
            obj.addProperty("fx", entry.getValue());
            entries.add(obj);
        }
        root.add("values", entries);
        return GSON.toJson(root);
    }

    public static Map<ResourceLocation, Long> fromJson(String json) {
        Map<ResourceLocation, Long> result = new LinkedHashMap<>();
        JsonObject root = GSON.fromJson(json, JsonObject.class);
        if (root == null || !root.has("values")) return result;
        JsonArray entries = root.getAsJsonArray("values");
        for (JsonElement elem : entries) {
            JsonObject obj = elem.getAsJsonObject();
            String itemStr = obj.get("item").getAsString();
            long fx = obj.get("fx").getAsLong();
            ResourceLocation id = ResourceLocation.tryParse(itemStr);
            if (id != null && fx > 0) {
                result.put(id, fx);
            }
        }
        return result;
    }

    public static String toToml(Map<ResourceLocation, Long> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("# OnixFlux FX Values\n");
        sb.append("# Format: \"item\" = value\n\n");
        sb.append("[values]\n");
        for (Map.Entry<ResourceLocation, Long> entry : values.entrySet()) {
            sb.append('"').append(entry.getKey()).append("\" = ").append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }

    public static Map<ResourceLocation, Long> fromToml(String toml) {
        Map<ResourceLocation, Long> result = new LinkedHashMap<>();
        String[] lines = toml.split("\n");
        boolean inValues = false;
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.startsWith("#") || trimmed.isEmpty()) continue;
            if (trimmed.equals("[values]")) { inValues = true; continue; }
            if (trimmed.startsWith("[")) { inValues = false; continue; }
            if (!inValues) continue;
            if (!trimmed.contains("=")) continue;
            String[] parts = trimmed.split("=", 2);
            String key = parts[0].trim().replaceAll("^\"|\"$", "");
            try {
                long value = Long.parseLong(parts[1].trim());
                ResourceLocation id = ResourceLocation.tryParse(key);
                if (id != null && value > 0) result.put(id, value);
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

    public static String toCsv(Map<ResourceLocation, Long> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("item,fx\n");
        for (Map.Entry<ResourceLocation, Long> entry : values.entrySet()) {
            sb.append(entry.getKey()).append(',').append(entry.getValue()).append('\n');
        }
        return sb.toString();
    }

    public static Map<ResourceLocation, Long> fromCsv(String csv) {
        Map<ResourceLocation, Long> result = new LinkedHashMap<>();
        String[] lines = csv.split("\n");
        boolean header = true;
        for (String line : lines) {
            if (header) { header = false; continue; }
            if (line.isBlank()) continue;
            String[] parts = line.split(",", 2);
            if (parts.length != 2) continue;
            ResourceLocation id = ResourceLocation.tryParse(parts[0].trim());
            try {
                long value = Long.parseLong(parts[1].trim());
                if (id != null && value > 0) result.put(id, value);
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

    public static CompoundTag toNbt(Map<ResourceLocation, Long> values) {
        CompoundTag tag = new CompoundTag();
        for (Map.Entry<ResourceLocation, Long> entry : values.entrySet()) {
            tag.putLong(entry.getKey().toString(), entry.getValue());
        }
        return tag;
    }

    public static Map<ResourceLocation, Long> fromNbt(CompoundTag tag) {
        Map<ResourceLocation, Long> result = new LinkedHashMap<>();
        for (String key : tag.getAllKeys()) {
            ResourceLocation id = ResourceLocation.tryParse(key);
            if (id != null) {
                result.put(id, tag.getLong(key));
            }
        }
        return result;
    }

}
