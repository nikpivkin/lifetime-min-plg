package io.github.nikpivkin.lifetime.commands;

import io.github.nikpivkin.lifetime.PluginService;
import io.github.nikpivkin.lifetime.localize.Localizer;
import io.github.nikpivkin.lifetime.localize.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class EnablePluginCommand implements Command {

  private final PluginService pluginService;
  private final Localizer localizer;

  public EnablePluginCommand(PluginService pluginService, Localizer localizer) {
    this.pluginService = pluginService;
    this.localizer = localizer;
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
    sender.sendMessage(Messages.COMMAND_ENABLE_OK);
    return true;
  }
}
