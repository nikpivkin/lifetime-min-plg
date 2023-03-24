package io.github.nikpivkin.lifetime.commands;

import io.github.nikpivkin.lifetime.LifetimeService;
import io.github.nikpivkin.lifetime.localize.Localizer;
import io.github.nikpivkin.lifetime.localize.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetLifetimeCommand implements Command {

  private final LifetimeService lifetimeService;
  private final Localizer localizer;

  public GetLifetimeCommand(LifetimeService lifetimeService, Localizer localizer) {
    this.lifetimeService = lifetimeService;
    this.localizer = localizer;
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
      sender.sendMessage(Messages.COMMAND_GET_USAGE);
      return false;
    }

    var playerName = args[1];
    var player = Bukkit.getPlayer(playerName);

    if (player == null) {
      sender.sendMessage(Messages.PLAYER_NOT_FOUND.formatted(playerName));
      return false;
    }

    var lifetime = lifetimeService.getLifeTime(player);
    sender.sendMessage(
        localizer.translate(player.locale(), Messages.PLAYER_HAS_LIFETIME, playerName, lifetime)
    );
    return true;
  }
}
