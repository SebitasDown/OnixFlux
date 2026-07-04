package com.sebitas.onixflux.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sebitas.onixflux.OnixFlux;
import com.sebitas.onixflux.config.ConfigManager;
import com.sebitas.onixflux.fx.FluxEngine;
import com.sebitas.onixflux.fx.FluxSource;
import com.sebitas.onixflux.network.dispatcher.ClientDispatcher;
import com.sebitas.onixflux.network.packet.LearnItemPacket;
import com.sebitas.onixflux.player.PlayerDataManager;
import com.sebitas.onixflux.registry.ModBlocks;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.Collection;

public final class OnixFluxCommand {

    private OnixFluxCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext ctx) {
        dispatcher.register(Commands.literal("onixflux")
                .requires(s -> s.hasPermission(2))

                .then(Commands.literal("give")
                        .executes(ctx2 -> giveTable(ctx2.getSource()))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx2 -> giveTable(ctx2.getSource(), EntityArgument.getPlayers(ctx2, "targets")))))

                .then(Commands.literal("learn")
                        .requires(s -> s.hasPermission(2))
                        .then(Commands.argument("item", ItemArgument.item(ctx))
                                .executes(ctx2 -> learnItem(ctx2.getSource(), ItemArgument.getItem(ctx2, "item")))
                                .then(Commands.argument("targets", EntityArgument.players())
                                        .executes(ctx2 -> learnItem(
                                                ctx2.getSource(),
                                                ItemArgument.getItem(ctx2, "item"),
                                                EntityArgument.getPlayers(ctx2, "targets"))))))

                .then(Commands.literal("value")
                        .requires(s -> s.hasPermission(2))
                        .then(Commands.argument("item", ItemArgument.item(ctx))
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ctx2 -> setValue(
                                                ctx2.getSource(),
                                                ItemArgument.getItem(ctx2, "item"),
                                                IntegerArgumentType.getInteger(ctx2, "amount"))))))

                .then(Commands.literal("reload")
                        .executes(ctx2 -> reloadConfig(ctx2.getSource())))

                .then(Commands.literal("clear")
                        .requires(s -> s.hasPermission(2))
                        .executes(ctx2 -> clearLearned(ctx2.getSource()))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx2 -> clearLearned(
                                        ctx2.getSource(),
                                        EntityArgument.getPlayers(ctx2, "targets")))))

                .then(Commands.literal("query")
                        .executes(ctx2 -> query(ctx2.getSource()))
                        .then(Commands.argument("item", ItemArgument.item(ctx))
                                .executes(ctx2 -> queryItem(
                                        ctx2.getSource(),
                                        ItemArgument.getItem(ctx2, "item")))))

                .then(Commands.literal("help")
                        .executes(ctx2 -> help(ctx2.getSource())))
        );
    }

    private static int giveTable(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack stack = new ItemStack(ModBlocks.FLUX_TABLE.get());
        player.addItem(stack);
        source.sendSuccess(() -> Component.translatable("command.onixflux.give.success", stack.getHoverName()), true);
        return 1;
    }

    private static int giveTable(CommandSourceStack source, Collection<ServerPlayer> targets) {
        ItemStack stack = new ItemStack(ModBlocks.FLUX_TABLE.get());
        for (ServerPlayer p : targets) {
            p.addItem(stack.copy());
        }
        source.sendSuccess(() -> Component.translatable("command.onixflux.give.multiple", targets.size()), true);
        return targets.size();
    }

    private static int learnItem(CommandSourceStack source, ItemInput itemInput) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack stack = itemInput.createItemStack(1, false);
        PlayerDataManager.learn(player, stack.getItem());
        source.sendSuccess(() -> Component.translatable("command.onixflux.learn.success", stack.getHoverName()), true);
        return 1;
    }

    private static int learnItem(CommandSourceStack source, ItemInput itemInput, Collection<ServerPlayer> targets) throws CommandSyntaxException {
        ItemStack stack = itemInput.createItemStack(1, false);
        for (ServerPlayer p : targets) {
            PlayerDataManager.learn(p, stack.getItem());
        }
        source.sendSuccess(() -> Component.translatable("command.onixflux.learn.multiple", stack.getHoverName(), targets.size()), true);
        return targets.size();
    }

    private static int setValue(CommandSourceStack source, ItemInput itemInput, int amount) throws CommandSyntaxException {
        ItemStack stack = itemInput.createItemStack(1, false);
        if (amount == 0) {
            FluxEngine.remove(stack.getItem());
            source.sendSuccess(() -> Component.translatable("command.onixflux.value.removed", stack.getHoverName()), true);
        } else {
            FluxEngine.register(stack.getItem(), amount, FluxSource.COMMAND);
            source.sendSuccess(() -> Component.translatable("command.onixflux.value.set", stack.getHoverName(), amount), true);
        }
        return 1;
    }

    private static int reloadConfig(CommandSourceStack source) {
        ConfigManager.reload();
        source.sendSuccess(() -> Component.translatable("command.onixflux.reload.success"), true);
        return 1;
    }

    private static int clearLearned(CommandSourceStack source) throws CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        PlayerDataManager.clearLearned(player);
        source.sendSuccess(() -> Component.translatable("command.onixflux.clear.success"), true);
        return 1;
    }

    private static int clearLearned(CommandSourceStack source, Collection<ServerPlayer> targets) {
        for (ServerPlayer p : targets) {
            PlayerDataManager.clearLearned(p);
        }
        source.sendSuccess(() -> Component.translatable("command.onixflux.clear.multiple", targets.size()), true);
        return targets.size();
    }

    private static int query(CommandSourceStack source) {
        source.sendSuccess(() -> Component.translatable("command.onixflux.query.count", FluxEngine.size()), true);
        return 1;
    }

    private static int queryItem(CommandSourceStack source, ItemInput itemInput) throws CommandSyntaxException {
        ItemStack stack = itemInput.createItemStack(1, false);
        long value = FluxEngine.getValue(stack.getItem()).map(v -> v.value()).orElse(0L);
        String src = FluxEngine.getValue(stack.getItem()).map(v -> v.source().name()).orElse("NONE");
        source.sendSuccess(() -> Component.translatable("command.onixflux.query.item", stack.getHoverName(), value, src), true);
        return 1;
    }

    private static int help(CommandSourceStack source) {
        source.sendSuccess(() -> Component.literal("§5===== OnixFlux Commands ====="), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux give §7- Get a Flux Table"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux give <player> §7- Give Flux Table to player"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux learn <item> §7- Learn an item"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux learn <item> <player> §7- Learn item for player"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux value <item> <amount> §7- Set FX value"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux reload §7- Reload config"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux clear §7- Clear learned items"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux query §7- Show FX stats"), false);
        source.sendSuccess(() -> Component.literal("§d/onixflux query <item> §7- Show item FX value"), false);
        return 1;
    }

}
