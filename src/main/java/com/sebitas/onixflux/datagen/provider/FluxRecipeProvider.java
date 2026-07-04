package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class FluxRecipeProvider extends RecipeProvider {

    public FluxRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> writer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, com.sebitas.onixflux.registry.ModBlocks.FLUX_TABLE.get())
                .pattern("EBE")
                .pattern("CSC")
                .pattern("WCW")
                .define('E', Items.EMERALD)
                .define('B', Items.ENCHANTING_TABLE)
                .define('C', Tags.Items.COBBLESTONE)
                .define('S', Tags.Items.STONE)
                .define('W', Tags.Items.FENCES_WOODEN)
                .unlockedBy("has_emerald", has(Items.EMERALD))
                .save(writer);
    }

}
