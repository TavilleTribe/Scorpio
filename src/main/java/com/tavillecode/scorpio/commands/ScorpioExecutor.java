package com.tavillecode.scorpio.commands;

import com.tavillecode.scorpio.Scorpio;
import com.tavillecode.scorpio.utils.MessageSection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ScorpioExecutor implements CommandExecutor {

    public ScorpioExecutor() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String s, String[] args) {
        if (sender instanceof Player player) {
            if (!player.isOp()) {
                return true;
            }
            if (args.length == 0) {
                player.performCommand("scorpio help");
            } else if (args.length == 1) {
                String parameter = args[0];
                if (parameter.equalsIgnoreCase("help")) {
                    MessageSection.HelpMessages(player);
                }
                if (parameter.equalsIgnoreCase("reload")) {
                    Scorpio.getYaml().reload();
                    player.sendMessage("§a重载成功!");
                    MessageSection.EnableMessages();
                }
            }
            /*
            else if (args.length == 2) {
                String p1 = args[0];
                String p2 = args[1];
                if (p1.equalsIgnoreCase("debug")) {
                    CompletableFuture.runAsync(() -> {
                        PlotHandler.updatePlot(player,"0",Integer.parseInt(p2));
                        try {
                            player.sendMessage(String.valueOf(PlotHandler.getCurrentPlot(player,"0")));
                        } catch (ExecutionException | InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
             */
        }
        return true;
    }
}

