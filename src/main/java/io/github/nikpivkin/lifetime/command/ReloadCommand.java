package io.github.nikpivkin.lifetime.command;

import io.github.nikpivkin.lifetime.PluginService;
import io.github.nikpivkin.lifetime.localize.Localizer;
import io.github.nikpivkin.lifetime.localize.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements Command {

  private final PluginService pluginService;
  private final Localizer localizer;

  public ReloadCommand(PluginService pluginService, Localizer localizer) {
    this.pluginService = pluginService;
    this.localizer = localizer;
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
    sender.sendMessage(Messages.COMMAND_RELOAD_OK);
    return true;
  }
}
