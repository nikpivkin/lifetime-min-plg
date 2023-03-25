package io.github.nikpivkin.lifetime.events

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

sealed class PluginStateChangedEvent : Event() {

    override fun getHandlers() = HANDLERS

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }

    class Enabled : PluginStateChangedEvent()
    class Disabled : PluginStateChangedEvent()
}
