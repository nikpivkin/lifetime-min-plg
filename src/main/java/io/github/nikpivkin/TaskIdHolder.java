package io.github.nikpivkin;

import java.util.Optional;
import org.bukkit.entity.Player;

public interface TaskIdHolder {

  Optional<Integer> get(Player player);

  void put(Player player, int taskId);

  void remove(Player player);

}
