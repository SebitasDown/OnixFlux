package com.sebitas.onixflux.config.datapack;

public record FluxValueJsonEntry(String item, String tag, long fx) {

    public boolean hasItem() {
        return item != null && !item.isBlank();
    }

    public boolean hasTag() {
        return tag != null && !tag.isBlank();
    }

    public boolean isValid() {
        return (hasItem() || hasTag()) && fx > 0;
    }

}
