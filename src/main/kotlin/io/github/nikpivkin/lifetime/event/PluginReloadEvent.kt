package io.github.nikpivkin.lifetime.event

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class PluginReloadEvent : Event() {

    override fun getHandlers() = HANDLERS

    companion object {
        private val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList() = HANDLERS
    }
}
