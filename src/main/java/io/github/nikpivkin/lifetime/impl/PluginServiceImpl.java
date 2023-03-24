package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.PluginConfig;
import io.github.nikpivkin.lifetime.PluginService;
import io.github.nikpivkin.lifetime.events.GodModeChangedEvent;
import io.github.nikpivkin.lifetime.events.PluginReloadEvent;
import io.github.nikpivkin.lifetime.events.PluginStateChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PluginServiceImpl implements PluginService {

  private final PluginConfig config;

  public PluginServiceImpl(PluginConfig config) {
    this.config = config;
  }

  @Override
  public void enable() {
    config.setEnabled(true);
    Bukkit.getPluginManager().callEvent(new PluginStateChangedEvent.Enabled());
  }

  @Override
  public void disable() {
    config.setEnabled(false);
    Bukkit.getPluginManager().callEvent(new PluginStateChangedEvent.Disabled());
  }

  @Override
  public void excludeUser(Player player) {
    config.excludeUser(player.getName());
    Bukkit.getPluginManager().callEvent(new GodModeChangedEvent.Disabled(player));
  }

  @Override
  public void includeUser(Player player) {
    config.includeUser(player.getName());
    Bukkit.getPluginManager().callEvent(new GodModeChangedEvent.Enabled(player));
  }

  @Override
  public void reload() {
    config.reload();
    Bukkit.getPluginManager().callEvent(new PluginReloadEvent());
  }
}
