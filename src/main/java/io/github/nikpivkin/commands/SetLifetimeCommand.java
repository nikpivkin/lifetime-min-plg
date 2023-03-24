package io.github.nikpivkin.commands;

import io.github.nikpivkin.Lifetime;
import io.github.nikpivkin.LifetimeService;
import io.github.nikpivkin.events.LifetimeChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetLifetimeCommand implements Command {

  private final LifetimeService lifetimeService;

  public SetLifetimeCommand(LifetimeService lifetimeService) {
    this.lifetimeService = lifetimeService;
  }

  @Override
  public @NotNull String name() {
    return "set";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    if (args.length < 3) {
      sender.sendMessage("Usage `set` command: /lifetime set <username> <amount>");
      return false;
    }
    var playerName = args[1];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(ChatColor.RED + "Player with name " + playerName + " not found");
      return false;
    }

    long amount;

    try {
      amount = Long.parseLong(args[2]);
    } catch (NumberFormatException nfe) {
      sender.sendMessage(ChatColor.RED + "invalid amount");
      return false;
    }

    var oldLifetime = lifetimeService.getLifeTime(player);
    var newLifetime = lifetimeService.setLifetime(player, new Lifetime(amount));
    Bukkit.getPluginManager().callEvent(new LifetimeChangedEvent(player, oldLifetime, newLifetime));
    sender.sendMessage("The lifetime of the " + playerName + " player is set to " + newLifetime);
    return true;
  }

}
