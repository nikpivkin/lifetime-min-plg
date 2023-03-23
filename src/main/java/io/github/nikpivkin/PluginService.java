package io.github.nikpivkin;

import org.bukkit.entity.Player;

public interface PluginService {

  void enable();

  void disable();

  void excludeUser(Player player);

  void includeUser(Player player);

  void reload();
}
