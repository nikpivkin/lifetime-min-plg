package io.github.nikpivkin.commands;

import io.github.nikpivkin.PluginService;
import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DisablePluginCommand implements Command {

  private final PluginService pluginService;
  private final Localizer localizer;

  public DisablePluginCommand(PluginService pluginService, Localizer localizer) {
    this.pluginService = pluginService;
    this.localizer = localizer;
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
    sender.sendMessage(Messages.COMMAND_DISABLE_OK);
    return true;
  }
}
