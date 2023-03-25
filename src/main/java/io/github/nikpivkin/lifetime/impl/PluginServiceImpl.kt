package io.github.nikpivkin.lifetime.impl

import io.github.nikpivkin.lifetime.PluginConfig
import io.github.nikpivkin.lifetime.PluginService
import io.github.nikpivkin.lifetime.event.GodModeChangedEvent
import io.github.nikpivkin.lifetime.event.PluginReloadEvent
import io.github.nikpivkin.lifetime.event.PluginStateChangedEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player

public class PluginServiceImpl(
    private val config: PluginConfig
) : PluginService {

    override fun enable() {
        config.setEnabled(true)
        Bukkit.getPluginManager().callEvent(PluginStateChangedEvent.Enabled())
    }

    override fun disable() {
        config.setEnabled(false)
        Bukkit.getPluginManager().callEvent(PluginStateChangedEvent.Disabled())
    }

    override fun excludeUser(player: Player) {
        config.excludeUser(player.name)
        Bukkit.getPluginManager().callEvent(GodModeChangedEvent.Disabled(player))
    }

    override fun includeUser(player: Player) {
        config.includeUser(player.name)
        Bukkit.getPluginManager().callEvent(GodModeChangedEvent.Enabled(player))
    }

    override fun reload() {
        config.reload()
        Bukkit.getPluginManager().callEvent(PluginReloadEvent())
    }
}
