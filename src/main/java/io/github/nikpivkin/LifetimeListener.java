package io.github.nikpivkin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class LifetimeListener implements Listener {

  private final LifetimeService lifetimeService;
  private final LifetimeTaskManager lifetimeTaskManager;
  private final PluginConfig config;

  public LifetimeListener(
      LifetimeService lifetimeService,
      LifetimeTaskManager lifetimeTaskManager,
      PluginConfig config
  ) {
    this.lifetimeService = lifetimeService;
    this.lifetimeTaskManager = lifetimeTaskManager;
    this.config = config;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (config.isDisabled()) {
      return;
    }

    var player = event.getPlayer();
    if (config.isUserExcluded(player.getName())) {
      return;
    }

    var lifetime = player.hasPlayedBefore()
        ? lifetimeService.getLifeTime(player)
        : lifetimeService.initLifeTime(player);

    player.showTitle(
        Title.title(
            Component.text("Твоя время: " + lifetime),
            Component.empty()
        )
    );

    lifetimeTaskManager.startupTask(player);
  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    if (config.isDisabled()) {
      return;
    }
    lifetimeTaskManager.cancelTask(event.getPlayer());
  }

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    if (config.isDisabled()) {
      return;
    }
    lifetimeTaskManager.startupTask(event.getPlayer());
  }

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) {
    if (config.isDisabled()) {
      return;
    }

    var player = event.getEntity();
    if (config.isUserExcluded(player.getName())) {
      return;
    }

    var killer = player.getKiller();

    lifetimeTaskManager.cancelTask(player);

    if (killer == null) {
      var lifetime = lifetimeService.takeTimeFromPlayer(player);
      player.sendMessage(
          Component
              .text(String.format("Ты умер и потерял %s секунд", lifetime))
              .color(NamedTextColor.RED)
      );
      return;
    }

    var lifetime = lifetimeService.transferLifetimeToAnotherPlayer(player, killer);
    player.sendMessage(
        Component
            .text(String.format(
                "Тебя убил игрок %s и ты потерял %s секунд", killer.getName(), lifetime
            ))
            .color(NamedTextColor.RED)
    );

    killer.sendMessage(
        Component
            .text(String.format(
                "Ты убил игрока %s и получаешь %s секунд в награду", player.getName(), lifetime
            ))
            .color(NamedTextColor.GREEN)
    );
  }

  @EventHandler
  public void onEntityDeathEvent(EntityDeathEvent event) {
    if (config.isDisabled()) {
      return;
    }

    var deader = event.getEntity();

    var killer = deader.getKiller();
    if (killer == null) {
      return;
    }

    lifetimeService.getLifetimeAsReward(killer, deader)
        .ifPresent(lifetime -> killer.sendMessage(
            Component
                .text(String.format(
                    "Ты убил %s и получаешь %s секунд в награду", deader.getName(), lifetime
                ))
                .color(NamedTextColor.GREEN)
        ));
  }
}
