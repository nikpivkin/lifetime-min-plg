package io.github.nikpivkin.commands;

import io.github.nikpivkin.PluginService;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EnablePluginCommand implements Command {

  private final PluginService pluginService;

  public EnablePluginCommand(PluginService pluginService) {
    this.pluginService = pluginService;
  }

  @Override
  public @NotNull String name() {
    return "enable";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    pluginService.enable();
    sender.sendMessage("Lifetime plugin has enabled");
    return true;
  }
}
