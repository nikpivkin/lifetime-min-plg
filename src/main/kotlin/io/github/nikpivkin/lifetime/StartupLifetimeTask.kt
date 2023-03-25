package io.github.nikpivkin.lifetime;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

class StartupLifetimeTask (
    private val lifetimeService: LifetimeService,
    private val config: PluginConfig,
    private val player: Player
): BukkitRunnable() {

  override fun run() {
    val currentLifeTime = lifetimeService.decrementLifetime(player)
    player.sendMessage(
        Component
            .text("Оставшееся время жизни: $currentLifeTime")
            .color(NamedTextColor.GREEN)
    )

    if (currentLifeTime.isOut) {
      config.onTimeOutCallback().apply(player)
      this.cancel()
    }
  }
}
