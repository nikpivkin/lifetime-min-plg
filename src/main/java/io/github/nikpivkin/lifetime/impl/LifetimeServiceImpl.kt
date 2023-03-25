package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.Lifetime
import io.github.nikpivkin.lifetime.LifetimeHolder
import io.github.nikpivkin.lifetime.LifetimeService
import io.github.nikpivkin.lifetime.PluginConfig
import org.bukkit.entity.Entity
import org.bukkit.entity.Mob
import org.bukkit.entity.Player
import java.util.*

class LifetimeServiceImpl (
  private val config: PluginConfig,
  private val lifetimeHolder: LifetimeHolder
) :  LifetimeService {

  override fun initLifeTime(player: Player ): Lifetime {
    return setLifetime(player, config.lifetimeAtStart());
  }

  override fun increaseLifeTime(player: Player, lifetime: Lifetime): Lifetime =
    setLifetime(player, getLifeTime(player).increase(lifetime))

  override fun decrementLifetime(player: Player): Lifetime =
    setLifetime(player, getLifeTime(player).decrement())

  override fun setLifetime(player: Player, lifetime: Lifetime): Lifetime {
    lifetimeHolder.put(player, lifetime);
    return lifetime;
  }

  override fun getLifeTime(player: Player): Lifetime {
    return lifetimeHolder.get(player).orElse(config.lifetimeAtStart());
  }

  override fun takeTimeFromPlayer(player: Player): Lifetime {
    return takeTimeFromPlayer(player, config.lifetimeLost());
  }

  override fun takeTimeFromPlayer(player: Player, percentage: Float): Lifetime {
    val playerLifeTime = getLifeTime(player);
    val timeTakenAway = playerLifeTime.mul(percentage);
    setLifetime(player, playerLifeTime.decrease(timeTakenAway));
    return timeTakenAway;
  }

  override fun transferLifetimeToAnotherPlayer(from: Player, to: Player): Lifetime {
    val time = takeTimeFromPlayer(from);
    increaseLifeTime(to, time);
    return time;
  }

  override fun getLifetimeAsReward(killer: Player, deadEntity: Entity): Optional<Lifetime> {

    if (deadEntity !is Mob) {
      return Optional.empty();
    }

    val lifetime = Lifetime(config.defaultReward().toLong()); // TODO

    increaseLifeTime(killer, lifetime);

    return Optional.of(lifetime);
  }
}
