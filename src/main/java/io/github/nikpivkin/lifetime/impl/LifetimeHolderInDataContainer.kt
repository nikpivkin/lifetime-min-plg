package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.Lifetime
import io.github.nikpivkin.lifetime.LifetimeHolder
import io.github.nikpivkin.lifetime.LifetimeTagType
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import java.util.*

class LifetimeHolderInDataContainer (
  private val key : NamespacedKey
) : LifetimeHolder {

  override fun put(player: Player, lifetime: Lifetime) {
    val dataContainer = player.persistentDataContainer;
    dataContainer.set(key, LifetimeTagType, lifetime);
  }

  override fun get(player: Player ) : Optional<Lifetime> {
    val dataContainer = player.persistentDataContainer;
    return Optional.ofNullable(
        dataContainer.get(key, LifetimeTagType)
    );
  }
}
