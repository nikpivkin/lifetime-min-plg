package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.Lifetime;
import io.github.nikpivkin.lifetime.LifetimeHolder;
import io.github.nikpivkin.lifetime.LifetimeService;
import io.github.nikpivkin.lifetime.PluginConfig;
import java.util.Optional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

public class LifetimeServiceImpl implements LifetimeService {

  private final PluginConfig config;
  private final LifetimeHolder lifetimeHolder;

  public LifetimeServiceImpl(
      PluginConfig config,
      LifetimeHolder lifetimeHolder
  ) {
    this.config = config;
    this.lifetimeHolder = lifetimeHolder;
  }

  public Lifetime initLifeTime(Player player) {
    return setLifetime(player, config.lifetimeAtStart());
  }

  public Lifetime increaseLifeTime(Player player, Lifetime lifetime) {
    return setLifetime(player, getLifeTime(player).increase(lifetime));
  }

  public Lifetime decrementLifetime(Player player) {
    return setLifetime(player, getLifeTime(player).decrement());
  }

  public Lifetime setLifetime(Player player, Lifetime lifetime) {
    lifetimeHolder.put(player, lifetime);
    return lifetime;
  }

  public Lifetime getLifeTime(Player player) {
    return lifetimeHolder.get(player).orElse(config.lifetimeAtStart());
  }

  public Lifetime takeTimeFromPlayer(Player player) {
    return takeTimeFromPlayer(player, config.lifetimeLost());
  }

  public Lifetime takeTimeFromPlayer(Player player, float percentage) {
    var playerLifeTime = getLifeTime(player);
    var timeTakenAway = playerLifeTime.mul(percentage);
    setLifetime(player, playerLifeTime.decrease(timeTakenAway));
    return timeTakenAway;
  }

  public Lifetime transferLifetimeToAnotherPlayer(Player from, Player to) {
    var time = takeTimeFromPlayer(from);
    increaseLifeTime(to, time);
    return time;
  }

  public Optional<Lifetime> getLifetimeAsReward(Player killer, Entity deadEntity) {
    if (!(deadEntity instanceof Mob)) {
      return Optional.empty();
    }

    var lifetime = new Lifetime(config.defaultReward()); // TODO

    increaseLifeTime(killer, lifetime);

    return Optional.of(lifetime);
  }
}
