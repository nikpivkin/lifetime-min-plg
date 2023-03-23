package io.github.nikpivkin.commands;

import io.github.nikpivkin.PluginService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements Command {

  private final PluginService pluginService;

  public ReloadCommand(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  @Override
  public @NotNull String name() {
    return "reload";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    pluginService.reload();
    sender.sendMessage("Config reloaded");
    return true;
  }
}
