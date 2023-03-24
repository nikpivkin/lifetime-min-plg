package io.github.nikpivkin.lifetime.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract sealed class GodModeChangedEvent extends PlayerEvent {

  private static final HandlerList HANDLERS = new HandlerList();

  private final boolean godModeEnabled;

  protected GodModeChangedEvent(@NotNull Player who, boolean godModeEnabled) {
    super(who);
    this.godModeEnabled = godModeEnabled;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public boolean isGodModeEnabled() {
    return godModeEnabled;
  }

  public static final class Enabled extends GodModeChangedEvent {

    public Enabled(@NotNull Player who) {
      super(who, true);
    }
  }

  public static final class Disabled extends GodModeChangedEvent {

    public Disabled(@NotNull Player who) {
      super(who, false);
    }
  }

}
