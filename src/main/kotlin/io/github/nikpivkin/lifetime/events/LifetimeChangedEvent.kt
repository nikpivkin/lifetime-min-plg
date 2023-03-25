package io.github.nikpivkin.lifetime.events;

import io.github.nikpivkin.lifetime.Lifetime
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

data class LifetimeChangedEvent(
  val player: Player,
  val oldValue: Lifetime,
  val newValue: Lifetime
): Event() {

  override fun getHandlers() = HANDLERS

  companion object {
    private val HANDLERS = HandlerList();

    @JvmStatic
    fun getHandlerList() = HANDLERS
  }
}
