package com.sebitas.onixflux.transmutation;

import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxValue;
import com.sebitas.onixflux.player.PlayerDataManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public final class FluxTableLogic {

    private FluxTableLogic() {}

    public static boolean tryLearn(ServerPlayer player, ItemStack stack, IItemHandler inventory, int learnSlot) {
        try {
            FluxTableValidation.checkStack(stack);

            java.util.Optional<FluxValue> opt = FluxEngine.getValue(stack.getItem());
            FluxTableValidation.checkHasValue(opt.isPresent());

            boolean known = PlayerDataManager.getLearnedItems(player).contains(stack.getItem());
            FluxTableValidation.checkItemUnknown(known);

            PlayerDataManager.learn(player, stack.getItem());

            inventory.extractItem(learnSlot, 1, false);

            return true;
        } catch (FluxTableException e) {
            return false;
        }
    }

    public static boolean tryTransmute(ServerPlayer player, Item item, int count, IItemHandler inventory, int outputSlot) {
        try {
            FluxTableValidation.checkItem(item);

            boolean known = PlayerDataManager.getLearnedItems(player).contains(item);
            FluxTableValidation.checkItemKnown(known);

            java.util.Optional<FluxValue> opt = FluxEngine.getValue(item);
            FluxTableValidation.checkHasValue(opt.isPresent());
            long fxCost = opt.get().value() * count;

            long playerFX = PlayerDataManager.getFlux(player);
            FluxTableValidation.checkSufficientFX(playerFX >= fxCost);

            ItemStack result = new ItemStack(item, count);
            inventory.insertItem(outputSlot, result, false);

            PlayerDataManager.removeFlux(player, fxCost);

            return true;
        } catch (FluxTableException e) {
            return false;
        }
    }

}
