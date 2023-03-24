package io.github.nikpivkin.commands;

import io.github.nikpivkin.LifetimeService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetLifetimeCommand implements Command {

  private final LifetimeService lifetimeService;

  public GetLifetimeCommand(LifetimeService lifetimeService) {
    this.lifetimeService = lifetimeService;
  }

  @Override
  public @NotNull String name() {
    return "get";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    if (args.length < 2) {
      sender.sendMessage("Usage `get` command: /lifetime get <username>");
      return false;
    }

    var playerName = args[1];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(ChatColor.RED + "Player with name " + playerName + " not found");
      return false;
    }

    var lifetime = lifetimeService.getLifeTime(player);
    sender.sendMessage(playerName + " player has " + lifetime + " seconds of lifetime");
    return true;
  }
}
