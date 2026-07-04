package com.sebitas.onixflux.datagen.provider;

import com.sebitas.onixflux.OnixFlux;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class FluxAdvancementProvider extends AdvancementProvider {

    public FluxAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, List.of(new FluxAdvancements()));
    }

    private static class FluxAdvancements implements AdvancementSubProvider {

        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> writer) {
            Advancement root = Advancement.Builder.advancement()
                    .display(com.sebitas.onixflux.registry.ModBlocks.FLUX_TABLE.get(),
                            Component.translatable("advancement.onixflux.root"),
                            Component.translatable("advancement.onixflux.root.desc"),
                            ResourceLocation.withDefaultNamespace("textures/block/enchanting_table_side.png"),
                            FrameType.TASK, false, false, false)
                    .addCriterion("has_table", InventoryChangeTrigger.TriggerInstance.hasItems(
                            com.sebitas.onixflux.registry.ModBlocks.FLUX_TABLE.get()))
                    .save(writer, ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "root").toString());

            Advancement firstLearn = Advancement.Builder.advancement()
                    .parent(root)
                    .display(Items.BOOK,
                            Component.translatable("advancement.onixflux.first_learn"),
                            Component.translatable("advancement.onixflux.first_learn.desc"),
                            null, FrameType.TASK, true, true, false)
                    .addCriterion("has_learned", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AIR))
                    .save(writer, ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "first_learn").toString());

            Advancement firstTransmute = Advancement.Builder.advancement()
                    .parent(firstLearn)
                    .display(Items.DIAMOND,
                            Component.translatable("advancement.onixflux.first_transmute"),
                            Component.translatable("advancement.onixflux.first_transmute.desc"),
                            null, FrameType.GOAL, true, true, false)
                    .addCriterion("has_transmuted", InventoryChangeTrigger.TriggerInstance.hasItems(Items.AIR))
                    .save(writer, ResourceLocation.fromNamespaceAndPath(OnixFlux.MOD_ID, "first_transmute").toString());
        }

    }

}
