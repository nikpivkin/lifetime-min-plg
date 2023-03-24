package io.github.nikpivkin.lifetime.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public interface Command {

  @NotNull String name();

  boolean execute(
      @NotNull CommandSender sender,
      @NotNull org.bukkit.command.Command command,
      @NotNull String label,
      @NotNull String[] args
  );
}
