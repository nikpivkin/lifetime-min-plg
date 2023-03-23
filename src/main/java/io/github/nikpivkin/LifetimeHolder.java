package io.github.nikpivkin;

import java.util.Optional;
import org.bukkit.entity.Player;

public interface LifetimeHolder {

  void put(Player player, Lifetime lifetime);

  Optional<Lifetime> get(Player player);
}
