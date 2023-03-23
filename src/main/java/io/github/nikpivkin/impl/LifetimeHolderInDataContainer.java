package io.github.nikpivkin.impl;

import io.github.nikpivkin.Lifetime;
import io.github.nikpivkin.LifetimeHolder;
import io.github.nikpivkin.LifetimeTagType;
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
