package io.github.nikpivkin.commands;

import io.github.nikpivkin.PluginService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GodModeCommand implements Command {

  private final PluginService pluginService;
  private final Plugin plugin;

  public GodModeCommand(PluginService pluginService, Plugin plugin) {
    this.pluginService = pluginService;
    this.plugin = plugin;
  }

  @Override
  public @NotNull String name() {
    return "god";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {

    if (args.length < 2) {
      sender.sendMessage("Usage `god` command: /lifetime god enable|disable <username> <time>?");
      return false;
    }

    var playerName = args[2];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(ChatColor.RED + "Player with name " + playerName + " not found");
      return false;
    }

    var subcommand = args[1];

    Runnable runnable;

    if ("enable".equalsIgnoreCase(subcommand)) {
      pluginService.excludeUser(player);
      sender.sendMessage("God mode is enabled for the player " + playerName);

      runnable = () -> {
        pluginService.includeUser(player);
        sender.sendMessage("the time of the god mode for the player has expired");
      };
    } else if ("disable".equalsIgnoreCase(subcommand)) {
      pluginService.includeUser(player);
      sender.sendMessage("God mode is disabled for the player " + playerName);

      runnable = () -> {
        pluginService.excludeUser(player);
        sender.sendMessage("the time of the god mode for the player has expired");
      };
    } else {
      sender.sendMessage(ChatColor.RED + "Invalid subcommand");
      return false;
    }

    if (args.length > 3) {
      long expires;

      try {
        expires = Long.parseLong(args[3]);
      } catch (NumberFormatException nfe) {
        sender.sendMessage("Invalid expires format");
        return false;
      }

      var taskId = Bukkit.getScheduler()
          .scheduleSyncDelayedTask(plugin, runnable, expires * 20);

      if (taskId == -1) {
        plugin.getLogger().severe("failed to schedule a command cancellation task");
      }
    }

    return true;
  }
}
