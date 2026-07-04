package com.sebitas.onixflux.client.tooltip;

import com.sebitas.onixflux.client.ClientConstants;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxValue;
import com.sebitas.onixflux.player.PlayerDataManager;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public final class FXTooltipHandler {

    private FXTooltipHandler() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getEntity() == null) return;

        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        Optional<FluxValue> value = FluxEngine.getValue(stack.getItem());
        if (value.isEmpty()) return;

        Player player = Minecraft.getInstance().player;
        if (player == null) return;

        boolean isLearned = PlayerDataManager.knows(player, stack.getItem());

        event.getToolTip().add(Component.literal(""));
        event.getToolTip().add(Component.translatable("tooltip.onixflux.fx_value", value.get().value())
                .withStyle(ChatFormatting.LIGHT_PURPLE));
        event.getToolTip().add(Component.translatable("tooltip.onixflux.fx_source", value.get().source().name())
                .withStyle(ChatFormatting.DARK_GRAY));

        if (isLearned) {
            event.getToolTip().add(Component.translatable("tooltip.onixflux.learned", "")
                    .withStyle(ChatFormatting.GREEN));
        } else {
            event.getToolTip().add(Component.translatable("tooltip.onixflux.not_learned")
                    .withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        }
    }

}
