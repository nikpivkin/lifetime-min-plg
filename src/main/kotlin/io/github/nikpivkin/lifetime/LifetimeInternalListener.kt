package io.github.nikpivkin.lifetime

import io.github.nikpivkin.lifetime.events.GodModeChangedEvent
import io.github.nikpivkin.lifetime.events.LifetimeChangedEvent
import io.github.nikpivkin.lifetime.events.PluginStateChangedEvent
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class LifetimeInternalListener(
    private val lifetimeTaskManager: LifetimeTaskManager
) : Listener {

    @EventHandler
    fun onGodModeEnabled(event: GodModeChangedEvent.Enabled) {
        lifetimeTaskManager.cancelTask(event.player)
    }

    @EventHandler
    fun onGodModeDisabled(event: GodModeChangedEvent.Disabled) {
        lifetimeTaskManager.startupTask(event.player)
    }

    @EventHandler
    fun onPluginEnabled(event: PluginStateChangedEvent.Enabled) {
        lifetimeTaskManager.startupTask(Bukkit.getOnlinePlayers())
    }

    @EventHandler
    fun onPluginDisabled(event: PluginStateChangedEvent.Disabled) {
        lifetimeTaskManager.cancelTask(Bukkit.getOnlinePlayers())
    }

    @EventHandler
    fun onLifetimeChanged(event: LifetimeChangedEvent) {
        if (event.newValue.isOut) {
            lifetimeTaskManager.cancelTask(event.player) // TODO kick user ?
        }
    }
}
