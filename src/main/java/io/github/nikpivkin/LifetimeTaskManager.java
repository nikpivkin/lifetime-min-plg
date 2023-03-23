package io.github.nikpivkin;

import java.util.Collection;
import org.bukkit.entity.Player;

public interface LifetimeTaskManager {

  int ONE_SECOND_IN_TICKS = 20;

  void startupTask(Player player);

  void startupTask(Collection<? extends Player> players);

  void cancelTask(Player player);

  void cancelTask(Collection<? extends Player> players);
}
