package io.github.nikpivkin.impl;

import io.github.nikpivkin.LifetimeService;
import io.github.nikpivkin.LifetimeTaskManager;
import io.github.nikpivkin.PluginConfig;
import io.github.nikpivkin.StartupLifetimeTask;
import io.github.nikpivkin.TaskIdHolder;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LifetimeTaskManagerImpl implements LifetimeTaskManager {

  private final PluginConfig config;
  private final LifetimeService lifetimeService;
  private final Plugin plugin;
  private final TaskIdHolder taskIdHolder;

  public LifetimeTaskManagerImpl(
      PluginConfig config,
      LifetimeService lifetimeService,
      Plugin plugin,
      TaskIdHolder taskIdHolder
  ) {
    this.config = config;
    this.lifetimeService = lifetimeService;
    this.plugin = plugin;
    this.taskIdHolder = taskIdHolder;
  }

  @Override
  public void startupTask(Player player) {
    if (config.isUserExcluded(player.getName())) {
      return;
    }

    var task = new StartupLifetimeTask(lifetimeService, config, player)
        .runTaskTimer(plugin, config.lifetimeStartAfter(), ONE_SECOND_IN_TICKS);

    taskIdHolder.put(player, task.getTaskId());
  }

  @Override
  public void startupTask(Collection<? extends Player> players) {
    players.forEach(this::startupTask);
  }

  @Override
  public void cancelTask(Player player) {
    if (config.isUserExcluded(player.getName())) {
      return;
    }
    taskIdHolder.get(player)
        .ifPresent(taskId -> {
          Bukkit.getScheduler().cancelTask(taskId);
          taskIdHolder.remove(player);
        });
  }

  @Override
  public void cancelTask(Collection<? extends Player> players) {
    players.forEach(this::cancelTask);
  }
}
