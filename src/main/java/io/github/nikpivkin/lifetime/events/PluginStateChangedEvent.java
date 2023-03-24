package io.github.nikpivkin.lifetime.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public abstract sealed class PluginStateChangedEvent extends Event {

  private static final HandlerList HANDLERS = new HandlerList();
  private final boolean enabled;

  protected PluginStateChangedEvent(boolean enabled) {
    this.enabled = enabled;
  }

  public static HandlerList getHandlerList() {
    return HANDLERS;
  }

  @Override
  public @NotNull HandlerList getHandlers() {
    return HANDLERS;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public static final class Enabled extends PluginStateChangedEvent {

    public Enabled() {
      super(true);
    }
  }

  public static final class Disabled extends PluginStateChangedEvent {

    public Disabled() {
      super(false);
    }
  }
}
