package io.github.nikpivkin;

import java.util.Optional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface LifetimeService {

  Lifetime initLifeTime(Player player);

  Lifetime increaseLifeTime(Player player, Lifetime lifetime);

  Lifetime decrementLifetime(Player player);

  Lifetime setLifetime(Player player, Lifetime lifetime);

  Lifetime getLifeTime(Player player);

  Lifetime takeTimeFromPlayer(Player player);

  Lifetime takeTimeFromPlayer(Player player, float percentage);

  Lifetime transferLifetimeToAnotherPlayer(Player from, Player to);

  Optional<Lifetime> getLifetimeAsReward(Player killer, Entity deadEntity);
}
