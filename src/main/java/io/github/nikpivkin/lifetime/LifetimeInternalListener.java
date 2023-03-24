package io.github.nikpivkin.lifetime;

import io.github.nikpivkin.lifetime.events.GodModeChangedEvent;
import io.github.nikpivkin.lifetime.events.LifetimeChangedEvent;
import io.github.nikpivkin.lifetime.events.PluginStateChangedEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class LifetimeInternalListener implements Listener {

  private final LifetimeTaskManager taskManager;

  public LifetimeInternalListener(LifetimeTaskManager taskManager) {
    this.taskManager = taskManager;
  }

  @EventHandler
  public void onGodModeEnabled(GodModeChangedEvent.Enabled event) {
    taskManager.cancelTask(event.getPlayer());
  }

  @EventHandler
  public void onGodModeDisabled(GodModeChangedEvent.Disabled event) {
    taskManager.startupTask(event.getPlayer());
  }

  @EventHandler
  public void onPluginEnabled(PluginStateChangedEvent.Enabled event) {
    taskManager.startupTask(Bukkit.getOnlinePlayers());
  }

  @EventHandler
  public void onPluginDisabled(PluginStateChangedEvent.Disabled event) {
    taskManager.cancelTask(Bukkit.getOnlinePlayers());
  }

  @EventHandler
  public void onLifetimeChanged(LifetimeChangedEvent event) {
    if (event.getNewValue().isOut()) {
      taskManager.cancelTask(event.getPlayer()); // TODO kick user ?
    }
  }
}
