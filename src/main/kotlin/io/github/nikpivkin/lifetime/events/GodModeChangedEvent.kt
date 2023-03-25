package io.github.nikpivkin.lifetime.events

import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

sealed class GodModeChangedEvent(player: Player) : Event() {
    override fun getHandlers() = HANDLERS

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }

    data class Enabled(val player: Player) : GodModeChangedEvent(player)
    data class Disabled(val player: Player) : GodModeChangedEvent(player)
}
