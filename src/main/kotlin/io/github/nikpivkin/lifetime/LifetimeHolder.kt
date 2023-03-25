package io.github.nikpivkin.lifetime;

import java.util.Optional;
import org.bukkit.entity.Player;

interface LifetimeHolder {
  fun put(player: Player, lifetime: Lifetime);
  fun get(player: Player): Optional<Lifetime>;
}
