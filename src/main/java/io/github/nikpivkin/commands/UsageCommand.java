package io.github.nikpivkin.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class UsageCommand implements Command {

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
    sender.sendMessage("Usage...");
    return true;
  }
}
