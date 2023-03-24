package io.github.nikpivkin.events;

import io.github.nikpivkin.Lifetime;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class LifetimeChangedEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();
  private final Player player;
  private final Lifetime oldValue;
  private final Lifetime newValue;

  public LifetimeChangedEvent(Player player, Lifetime oldValue, Lifetime newValue) {
    this.player = player;
    this.oldValue = oldValue;
    this.newValue = newValue;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  public Player getPlayer() {
    return player;
  }

  public Lifetime getOldValue() {
    return oldValue;
  }

  public Lifetime getNewValue() {
    return newValue;
  }
}
