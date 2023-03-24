package io.github.nikpivkin;

import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.Messages;
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
  private final Localizer localizer;

  public LifetimeListener(
      LifetimeService lifetimeService,
      LifetimeTaskManager lifetimeTaskManager,
      PluginConfig config,
      Localizer localizer
  ) {
    this.lifetimeService = lifetimeService;
    this.lifetimeTaskManager = lifetimeTaskManager;
    this.config = config;
    this.localizer = localizer;
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
            Component.text(
                localizer.translate(player.locale(), Messages.PLAYER_YOUR_TIME, lifetime)),
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
              .text(
                  localizer.translate(player.locale(), Messages.PLAYER_YOU_DIED_AND_LOST, lifetime))
              .color(NamedTextColor.RED)
      );
      return;
    }

    var lifetime = lifetimeService.transferLifetimeToAnotherPlayer(player, killer);
    player.sendMessage(
        Component
            .text(
                localizer.translate(
                    player.locale(),
                    Messages.PLAYER_YOU_WERE_KILLED_AND_YOU_LOST,
                    killer.getName(), lifetime
                )
            )
            .color(NamedTextColor.RED)
    );

    killer.sendMessage(
        Component
            .text(
                localizer.translate(
                    killer.locale(),
                    Messages.PLAYER_YOU_KILLED_AND_GOT,
                    player.getName(), lifetime
                )
            )
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
                .text(
                    localizer.translate(
                        killer.locale(),
                        Messages.PLAYER_YOU_KILLED_AND_GOT,
                        deader.getName(), lifetime
                    )
                )
                .color(NamedTextColor.GREEN)
        ));
  }
}
