package com.sebitas.onixflux.fx;

import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Optional;

public final class FluxEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger("FluxEngine");
    private static final FluxRegistry REGISTRY = new FluxRegistry();
    private static final FluxDiagnostics DIAGNOSTICS = new FluxDiagnostics();
    private static volatile FluxState state = FluxState.UNINITIALIZED;

    private FluxEngine() {
    }

    public static void register(Item item, long value, FluxSource source) {
        FluxValidation.checkItem(item);
        FluxValidation.checkValue(value);
        FluxValidation.checkSource(source);
        FluxValidation.checkNotFrozen(isFrozen());
        REGISTRY.register(item, new FluxValue(value, source));
    }

    public static Optional<FluxValue> getValue(Item item) {
        FluxValidation.checkItem(item);
        return REGISTRY.get(item);
    }

    public static boolean hasValue(Item item) {
        FluxValidation.checkItem(item);
        return REGISTRY.contains(item);
    }

    public static void remove(Item item) {
        FluxValidation.checkItem(item);
        FluxValidation.checkNotFrozen(isFrozen());
        REGISTRY.remove(item);
    }

    public static void clear() {
        FluxValidation.checkNotFrozen(isFrozen());
        REGISTRY.clear();
    }

    public static void reload() {
        LOGGER.info("Reloading FluxEngine...");
        REGISTRY.clear();
        state = FluxState.UNINITIALIZED;
        FluxBootstrap.reset();
        FluxBootstrap.bootstrap();
        FluxBootstrap.loadConfig();
        LOGGER.info("FluxEngine reloaded with {} values (unfrozen, awaiting recipe calculation)", REGISTRY.size());
    }

    public static Map<Item, FluxValue> getAllValues() {
        return REGISTRY.getAll();
    }

    public static int size() {
        return REGISTRY.size();
    }

    public static boolean isFrozen() {
        return state == FluxState.FROZEN;
    }

    public static FluxState state() {
        return state;
    }

    public static FluxDiagnostics diagnostics() {
        return DIAGNOSTICS;
    }

    static void transitionTo(FluxState newState) {
        state = newState;
        LOGGER.debug("FluxEngine state: {} -> {}", state, newState);
    }

    static void freeze() {
        transitionTo(FluxState.FROZEN);
        DIAGNOSTICS.recordItems(REGISTRY.size());
        DIAGNOSTICS.log(LOGGER);
        LOGGER.info("FluxEngine frozen with {} registered values", REGISTRY.size());
    }

    public static void removeCalculatedValues() {
        FluxValidation.checkNotFrozen(isFrozen());
        REGISTRY.removeCalculated();
    }

    static FluxRegistry registry() {
        return REGISTRY;
    }

    public static Logger logger() {
        return LOGGER;
    }

}
