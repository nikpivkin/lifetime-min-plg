package io.github.nikpivkin.commands;

import io.github.nikpivkin.PluginService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DisablePluginCommand implements Command {

  private final PluginService pluginService;

  public DisablePluginCommand(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  @Override
  public @NotNull String name() {
    return "disable";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    pluginService.disable();
    sender.sendMessage("Lifetime plugin has disabled");
    return true;
  }
}
