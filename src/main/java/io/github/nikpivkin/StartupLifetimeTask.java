package io.github.nikpivkin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartupLifetimeTask extends BukkitRunnable {

  private final LifetimeService lifetimeService;
  private final PluginConfig config;
  private final Player player;

  public StartupLifetimeTask(
      LifetimeService lifetimeService,
      PluginConfig config,
      Player player
  ) {
    this.lifetimeService = lifetimeService;
    this.config = config;
    this.player = player;
  }

  @Override
  public void run() {
    var currentLifeTime = lifetimeService.decrementLifetime(player);
    player.sendMessage(
        Component
            .text("Оставшееся время жизни: " + currentLifeTime)
            .color(NamedTextColor.GREEN)
    );

    if (currentLifeTime.isOut()) {
      config.onTimeOutCallback().apply(player);
      this.cancel();
    }
  }
}
