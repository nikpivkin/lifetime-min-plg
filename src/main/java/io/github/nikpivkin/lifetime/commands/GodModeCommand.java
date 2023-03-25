package io.github.nikpivkin.lifetime.commands;

import io.github.nikpivkin.lifetime.PluginService;
import io.github.nikpivkin.lifetime.localize.Localizer;
import io.github.nikpivkin.lifetime.localize.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class GodModeCommand implements Command {

  private final PluginService pluginService;
  private final Plugin plugin;
  private final Localizer localizer;

  public GodModeCommand(PluginService pluginService, Plugin plugin, Localizer localizer) {
    this.pluginService = pluginService;
    this.plugin = plugin;
    this.localizer = localizer;
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

    if (args.length < 3) {
      sender.sendMessage(Messages.COMMAND_GOD_MODE_USAGE);
      return false;
    }

    var playerName = args[2];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(Messages.PLAYER_NOT_FOUND.formatted(playerName));
      return false;
    }

    var subcommand = args[1];

    Runnable runnable;

    if ("enable".equalsIgnoreCase(subcommand)) {
      pluginService.excludeUser(player);
      sender.sendMessage(Messages.COMMAND_GOD_MODE_ENABLED.formatted(playerName));

      runnable = () -> {
        pluginService.includeUser(player);
        sender.sendMessage(Messages.COMMAND_GOD_MODE_EXPIRED.formatted(playerName));
      };
    } else if ("disable".equalsIgnoreCase(subcommand)) {
      pluginService.includeUser(player);
      sender.sendMessage(Messages.COMMAND_GOD_MODE_DISABLED.formatted(playerName));

      runnable = () -> {
        pluginService.excludeUser(player);
        sender.sendMessage(Messages.COMMAND_GOD_MODE_EXPIRED.formatted(playerName));
      };
    } else {
      sender.sendMessage(ChatColor.RED + Messages.INVALID_SUBCOMMAND);
      return false;
    }

    if (args.length > 4) {
      long expires;

      try {
        expires = Long.parseLong(args[3]);
      } catch (NumberFormatException nfe) {
        sender.sendMessage(Messages.COMMAND_GOD_MODE_INVALID_EXPIRES);
        return false;
      }

      var taskId = Bukkit.getScheduler()
          .scheduleSyncDelayedTask(plugin, runnable, expires * 20);

      if (taskId == -1) {
        plugin.getLogger().severe(Messages.COMMAND_GOD_MODE_FAILED_SCHEDULE_TASK);
      }
    }

    return true;
  }
}
