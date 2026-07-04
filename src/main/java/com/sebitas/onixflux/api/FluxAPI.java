package com.sebitas.onixflux.api;

import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.fx.FluxValue;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class FluxAPI {

    private static volatile FluxAPIImpl impl;

    private FluxAPI() {}

    public static synchronized void initialize() {
        if (impl != null) return;
        FluxServices.initialize();
        impl = new FluxAPIImpl();
    }

    private static FluxAPIImpl impl() {
        if (impl == null) {
            throw new FluxApiException("FluxAPI not initialized. Call FluxAPI.initialize() during mod construction.");
        }
        return impl;
    }

    /**
     * Registers an item with the given FX value using the API source.
     *
     * @param item  the item to register
     * @param value the FX value (must be positive)
     * @throws FluxApiException if the item is null, AIR, value is not positive,
     *                          or the engine is frozen
     */
    public static void register(Item item, long value) {
        impl().register(item, value);
    }

    /**
     * Registers an item with the given FX value and explicit source.
     *
     * @param item   the item to register
     * @param value  the FX value (must be positive)
     * @param source the source type
     * @throws FluxApiException if any parameter is invalid or the engine is frozen
     */
    public static void register(Item item, long value, FluxSource source) {
        impl().register(item, value, source);
    }

    /**
     * Removes an item from the FX registry.
     *
     * @param item the item to remove
     * @throws FluxApiException if the item is null or the engine is frozen
     */
    public static void remove(Item item) {
        impl().remove(item);
    }

    /**
     * Replaces the FX value of an item, or registers it if not present.
     *
     * @param item  the item
     * @param value the new FX value
     * @throws FluxApiException if parameters are invalid
     */
    public static void replace(Item item, long value) {
        impl().replace(item, value);
    }

    /**
     * Forcefully overrides the FX value regardless of existing registration.
     *
     * @param item  the item
     * @param value the new FX value
     * @throws FluxApiException if parameters are invalid
     */
    public static void override(Item item, long value) {
        impl().override(item, value);
    }

    /**
     * Registers a batch of items with their FX values.
     *
     * @param values map of item to FX value
     * @throws FluxApiException if the map is null or any entry is invalid
     */
    public static void registerAll(Map<Item, Long> values) {
        impl().registerAll(values);
    }

    /**
     * Clears all registered FX values from the engine.
     *
     * @throws FluxApiException if the engine is frozen
     */
    public static void clear() {
        impl().clear();
    }

    /**
     * Gets the FX value for an item, if registered.
     *
     * @param item the item to query
     * @return an Optional containing the FluxValue, or empty if not registered
     */
    public static Optional<FluxValue> getValue(Item item) {
        return impl().getValue(item);
    }

    /**
     * Gets the numeric FX value for an item, or 0 if not registered.
     *
     * @param item the item to query
     * @return the FX value, or 0 if not registered
     */
    public static long getLongValue(Item item) {
        return impl().getLongValue(item);
    }

    /**
     * Gets the numeric FX value for an item stack, or 0 if not registered.
     *
     * @param stack the stack to query
     * @return the FX value, or 0 if not registered
     */
    public static long getLongValue(ItemStack stack) {
        return impl().getLongValue(stack);
    }

    /**
     * Checks if an item has a registered FX value.
     *
     * @param item the item to check
     * @return true if the item has a registered FX value
     */
    public static boolean hasValue(Item item) {
        return impl().hasValue(item);
    }

    /**
     * Checks if an item stack's item has a registered FX value.
     *
     * @param stack the stack to check
     * @return true if the item has a registered FX value
     */
    public static boolean hasValue(ItemStack stack) {
        return impl().hasValue(stack);
    }

    /**
     * Gets the source of an item's FX value.
     *
     * @param item the item to query
     * @return the FluxSource, or null if not registered
     */
    public static FluxSource getSource(Item item) {
        return impl().getSource(item);
    }

    /**
     * Gets an unmodifiable view of all registered FX values.
     *
     * @return map of item to FluxValue
     */
    public static Map<Item, FluxValue> getAllValues() {
        return impl().getAllValues();
    }

    /**
     * Returns the number of registered FX values.
     */
    public static int valueCount() {
        return impl().valueCount();
    }

    /**
     * Checks if the FX engine is in a frozen state.
     */
    public static boolean isFrozen() {
        return impl().isFrozen();
    }

    /**
     * Gets the current FX amount for a player.
     *
     * @param player the player
     * @return the player's FX amount
     */
    public static long getFlux(Player player) {
        return impl().getFlux(player);
    }

    /**
     * Sets the FX amount for a player.
     *
     * @param player the player
     * @param value  the new FX amount (must be non-negative)
     */
    public static void setFlux(Player player, long value) {
        impl().setFlux(player, value);
    }

    /**
     * Adds FX to a player's balance.
     *
     * @param player the player
     * @param amount the amount to add (must be positive)
     * @return true if the operation succeeded
     */
    public static boolean addFlux(Player player, long amount) {
        return impl().addFlux(player, amount);
    }

    /**
     * Removes FX from a player's balance.
     *
     * @param player the player
     * @param amount the amount to remove (must be positive)
     * @return true if the operation succeeded
     */
    public static boolean removeFlux(Player player, long amount) {
        return impl().removeFlux(player, amount);
    }

    /**
     * Teaches a player an item, adding it to their known items.
     *
     * @param player the player
     * @param item   the item to learn
     * @return true if the item was newly learned
     */
    public static boolean learn(Player player, Item item) {
        return impl().learn(player, item);
    }

    /**
     * Removes an item from a player's knowledge.
     *
     * @param player the player
     * @param item   the item to forget
     * @return true if the item was forgotten
     */
    public static boolean forget(Player player, Item item) {
        return impl().forget(player, item);
    }

    /**
     * Checks if a player knows a particular item.
     *
     * @param player the player
     * @param item   the item to check
     * @return true if the player knows the item
     */
    public static boolean knows(Player player, Item item) {
        return impl().knows(player, item);
    }

    /**
     * Gets an unmodifiable set of all items a player has learned.
     *
     * @param player the player
     * @return set of learned items
     */
    public static Set<Item> getKnowledge(Player player) {
        return impl().getKnowledge(player);
    }

    /**
     * Returns the number of items a player has learned.
     *
     * @param player the player
     * @return count of learned items
     */
    public static int learnedCount(Player player) {
        return impl().learnedCount(player);
    }

    /**
     * Checks if a player has any FX.
     *
     * @param player the player
     * @return true if the player has FX
     */
    public static boolean hasFlux(Player player) {
        return impl().hasFlux(player);
    }

    /**
     * Registers a transmutation recipe.
     *
     * @param recipe the recipe to register
     */
    public static void registerRecipe(FluxRecipe recipe) {
        impl().registerRecipe(recipe);
    }

    /**
     * Removes a recipe by its ID.
     *
     * @param id the recipe ID
     */
    public static void removeRecipe(ResourceLocation id) {
        impl().removeRecipe(id);
    }

    /**
     * Gets a recipe by its ID.
     *
     * @param id the recipe ID
     * @return an Optional containing the recipe, or empty
     */
    public static Optional<FluxRecipe> getRecipe(ResourceLocation id) {
        return impl().getRecipe(id);
    }

    /**
     * Gets all registered recipes.
     *
     * @return unmodifiable collection of recipes
     */
    public static Collection<FluxRecipe> getAllRecipes() {
        return impl().getAllRecipes();
    }

    /**
     * Checks if a recipe with the given ID exists.
     */
    public static boolean hasRecipe(ResourceLocation id) {
        return impl().hasRecipe(id);
    }

    /**
     * Returns the number of registered recipes.
     */
    public static int recipeCount() {
        return impl().recipeCount();
    }

    /**
     * Finds a recipe that produces the given item stack.
     *
     * @param stack the output stack to match
     * @return the matching recipe, or null
     */
    public static FluxRecipe findRecipeByOutput(ItemStack stack) {
        return impl().findRecipeByOutput(stack);
    }

    /**
     * Clears all registered recipes.
     */
    public static void clearRecipes() {
        impl().clearRecipes();
    }

}
