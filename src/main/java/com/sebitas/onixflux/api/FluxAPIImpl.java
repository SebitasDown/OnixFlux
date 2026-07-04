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

public final class FluxAPIImpl {

    FluxAPIImpl() {}

    public void register(Item item, long value) {
        FluxServices.registration().register(item, value);
    }

    public void register(Item item, long value, FluxSource source) {
        FluxServices.registration().register(item, value, source);
    }

    public void remove(Item item) {
        FluxServices.registration().remove(item);
    }

    public void replace(Item item, long value) {
        FluxServices.registration().replace(item, value);
    }

    public void override(Item item, long value) {
        FluxServices.registration().override(item, value);
    }

    public void registerAll(Map<Item, Long> values) {
        FluxServices.registration().registerAll(values);
    }

    public void clear() {
        FluxServices.registration().clear();
    }

    public Optional<FluxValue> getValue(Item item) {
        return FluxServices.lookup().getValue(item);
    }

    public long getLongValue(Item item) {
        return FluxServices.lookup().getLongValue(item);
    }

    public long getLongValue(ItemStack stack) {
        return FluxServices.lookup().getLongValue(stack);
    }

    public boolean hasValue(Item item) {
        return FluxServices.lookup().hasValue(item);
    }

    public boolean hasValue(ItemStack stack) {
        return FluxServices.lookup().hasValue(stack);
    }

    public FluxSource getSource(Item item) {
        return FluxServices.lookup().getSource(item);
    }

    public Map<Item, FluxValue> getAllValues() {
        return FluxServices.lookup().getAllValues();
    }

    public int valueCount() {
        return FluxServices.lookup().size();
    }

    public boolean isFrozen() {
        return FluxServices.lookup().isFrozen();
    }

    public long getFlux(Player player) {
        return FluxServices.player().getFlux(player);
    }

    public void setFlux(Player player, long value) {
        FluxServices.player().setFlux(player, value);
    }

    public boolean addFlux(Player player, long amount) {
        return FluxServices.player().addFlux(player, amount);
    }

    public boolean removeFlux(Player player, long amount) {
        return FluxServices.player().removeFlux(player, amount);
    }

    public boolean learn(Player player, Item item) {
        return FluxServices.player().learn(player, item);
    }

    public boolean forget(Player player, Item item) {
        return FluxServices.player().forget(player, item);
    }

    public boolean knows(Player player, Item item) {
        return FluxServices.player().knows(player, item);
    }

    public Set<Item> getKnowledge(Player player) {
        return FluxServices.player().getKnowledge(player);
    }

    public int learnedCount(Player player) {
        return FluxServices.player().learnedCount(player);
    }

    public boolean hasFlux(Player player) {
        return FluxServices.player().hasFlux(player);
    }

    public void registerRecipe(FluxRecipe recipe) {
        FluxServices.recipe().registerRecipe(recipe);
    }

    public void removeRecipe(ResourceLocation id) {
        FluxServices.recipe().removeRecipe(id);
    }

    public Optional<FluxRecipe> getRecipe(ResourceLocation id) {
        return FluxServices.recipe().getRecipe(id);
    }

    public Collection<FluxRecipe> getAllRecipes() {
        return FluxServices.recipe().getAllRecipes();
    }

    public boolean hasRecipe(ResourceLocation id) {
        return FluxServices.recipe().hasRecipe(id);
    }

    public int recipeCount() {
        return FluxServices.recipe().recipeCount();
    }

    public FluxRecipe findRecipeByOutput(ItemStack stack) {
        return FluxServices.recipe().findRecipeByOutput(stack);
    }

    public void clearRecipes() {
        FluxServices.recipe().clearRecipes();
    }

}
