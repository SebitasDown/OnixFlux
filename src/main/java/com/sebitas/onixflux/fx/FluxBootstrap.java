package com.sebitas.onixflux.fx;

import com.sebitas.onixflux.fx.provider.ApiProvider;
import com.sebitas.onixflux.fx.provider.ConfigProvider;
import com.sebitas.onixflux.fx.provider.RecipeProvider;
import com.sebitas.onixflux.fx.provider.VanillaProvider;
import com.sebitas.onixflux.fx.recipe.FluxRecipe;
import com.sebitas.onixflux.fx.recipe.FluxRecipeLoader;
import com.sebitas.onixflux.fx.recipe.RecipeNormalizer;
import com.sebitas.onixflux.fx.recipe.RecipeResolver;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

public final class FluxBootstrap {

    private static boolean bootstrapped = false;

    private FluxBootstrap() {
    }

    public static void bootstrap() {
        if (bootstrapped) {
            FluxEngine.logger().warn("FluxBootstrap already executed. Skipping.");
            return;
        }
        bootstrapped = true;

        long start = System.nanoTime();
        FluxEngine.logger().info("FluxBootstrap: phase 1 - registering vanilla defaults");
        new VanillaProvider().provide();
        FluxEngine.transitionTo(FluxState.BOOTSTRAPPED);
        FluxEngine.diagnostics().recordManual(FluxEngine.registry().countBySource(FluxSource.DEFAULT));
        long elapsed = System.nanoTime() - start;
        FluxEngine.logger().info("FluxBootstrap: phase 1 complete ({} items) in {} ms",
                FluxEngine.registry().size(), elapsed / 1_000_000);
    }

    public static void loadConfig() {
        long start = System.nanoTime();
        FluxEngine.logger().info("FluxBootstrap: phase 2 - loading configuration");

        new ConfigProvider().provide();
        new ApiProvider().provide();

        FluxEngine.transitionTo(FluxState.CONFIG_LOADED);
        FluxEngine.diagnostics().recordManual(
                FluxEngine.registry().countBySource(FluxSource.DEFAULT) +
                FluxEngine.registry().countBySource(FluxSource.CONFIG) +
                FluxEngine.registry().countBySource(FluxSource.API));
        long elapsed = System.nanoTime() - start;
        FluxEngine.logger().info("FluxBootstrap: phase 2 complete ({} items) in {} ms",
                FluxEngine.registry().size(), elapsed / 1_000_000);
    }

    public static void finalize(RecipeManager recipeManager, RegistryAccess registryAccess) {
        if (FluxEngine.isFrozen()) {
            FluxEngine.logger().warn("FluxEngine already finalized. Skipping.");
            return;
        }

        long start = System.nanoTime();

        List<FluxRecipe> rawRecipes = FluxRecipeLoader.loadAll(recipeManager, registryAccess);
        FluxEngine.diagnostics().recordRecipes(rawRecipes.size());

        List<FluxRecipe> recipes = RecipeNormalizer.normalize(rawRecipes);
        RecipeResolver resolver = new RecipeResolver(recipes);
        FluxEngine.diagnostics().recordRecipes(resolver.totalRecipes());
        FluxEngine.logger().info("FluxBootstrap: phase 3 - loaded {} recipes ({} normalized, {} distinct outputs)",
                rawRecipes.size(), recipes.size(), resolver.distinctOutputs());

        FluxEngine.logger().info("FluxBootstrap: phase 4 - calculating recipes");
        new RecipeProvider(recipes).provide();

        FluxEngine.transitionTo(FluxState.RECIPES_CALCULATED);
        int total = FluxEngine.registry().size();
        FluxEngine.logger().info("FluxBootstrap: phase 5 - finalizing ({} items registered)", total);

        long elapsed = System.nanoTime() - start;
        FluxEngine.diagnostics().recordCalculationTime(elapsed);
        FluxEngine.diagnostics().recordItems(total);

        FluxEngine.freeze();
    }

    public static void reset() {
        bootstrapped = false;
    }

    public static boolean isBootstrapped() {
        return bootstrapped;
    }

}
