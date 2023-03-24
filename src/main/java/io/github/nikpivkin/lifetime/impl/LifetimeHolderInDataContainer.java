package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.Lifetime;
import io.github.nikpivkin.lifetime.LifetimeHolder;
import io.github.nikpivkin.lifetime.LifetimeTagType;
import java.util.Optional;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

public final class LifetimeHolderInDataContainer implements LifetimeHolder {

  private final NamespacedKey key;

  public LifetimeHolderInDataContainer(NamespacedKey key) {
    this.key = key;
  }

  @Override
  public void put(Player player, Lifetime lifetime) {
    var dataContainer = player.getPersistentDataContainer();
    dataContainer.set(key, LifetimeTagType.INSTANCE, lifetime);
  }

  @Override
  public Optional<Lifetime> get(Player player) {
    var dataContainer = player.getPersistentDataContainer();
    return Optional.ofNullable(
        dataContainer.get(key, LifetimeTagType.INSTANCE)
    );
  }
}
