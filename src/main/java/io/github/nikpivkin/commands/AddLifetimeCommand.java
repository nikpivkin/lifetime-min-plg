package io.github.nikpivkin.commands;

import io.github.nikpivkin.Lifetime;
import io.github.nikpivkin.LifetimeService;
import io.github.nikpivkin.events.LifetimeChangedEvent;
import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AddLifetimeCommand implements Command {

  private final LifetimeService lifetimeService;
  private final Localizer localizer;

  public AddLifetimeCommand(LifetimeService lifetimeService, Localizer localizer) {
    this.lifetimeService = lifetimeService;
    this.localizer = localizer;
  }

  @Override
  public @NotNull String name() {
    return "add";
  }

  @Override
  public boolean execute(
      @NotNull CommandSender sender,
      org.bukkit.command.@NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {

    if (args.length < 3) {

      sender.sendMessage(Messages.COMMAND_ADD_USAGE);
      return false;
    }
    var playerName = args[1];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(ChatColor.RED + Messages.PLAYER_NOT_FOUND.formatted(playerName));
      return false;
    }

    var local = localizer.translate(player.locale());

    long amount;

    try {
      amount = Long.parseLong(args[2]);
    } catch (NumberFormatException nfe) {
      sender.sendMessage(ChatColor.RED + local.apply(Messages.COMMAND_ADD_INVALID_AMOUNT));
      return false;
    }

    Bukkit.getLogger().info(Bukkit.getConsoleSender().getName());

    var oldLifetime = lifetimeService.getLifeTime(player);
    var newLifetime = lifetimeService.increaseLifeTime(player, new Lifetime(amount));
    Bukkit.getPluginManager().callEvent(new LifetimeChangedEvent(player, oldLifetime, newLifetime));
    sender.sendMessage(local.apply(Messages.PLAYER_HAS_LIFETIME, playerName, amount));
    return true;
  }
}
