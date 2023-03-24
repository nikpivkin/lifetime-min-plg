package io.github.nikpivkin.commands;

import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.Messages;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UsageCommand implements Command {

  private final Localizer localizer;

  public UsageCommand(Localizer localizer) {
    this.localizer = localizer;
  }

  @Override
  public @NotNull String name() {
    return "help";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args) {
    sender.sendMessage(Messages.COMMAND_USAGE_USAGE);
    return true;
  }
}
