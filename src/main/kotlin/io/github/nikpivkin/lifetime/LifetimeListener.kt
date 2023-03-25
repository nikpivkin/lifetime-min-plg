package io.github.nikpivkin.lifetime

import io.github.nikpivkin.lifetime.localize.Localizer
import io.github.nikpivkin.lifetime.localize.Messages
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.player.PlayerRespawnEvent

class LifetimeListener(
    private val lifetimeService: LifetimeService,
    private val lifetimeTaskManager: LifetimeTaskManager,
    private val config: PluginConfig,
    private val localizer: Localizer
) : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        if (config.isDisabled) {
            return
        }

        val player = event.player
        if (config.isUserExcluded(player.name)) {
            return
        }

        val lifetime = if (player.hasPlayedBefore()) {
            lifetimeService.getLifeTime(player)
        } else {
            lifetimeService.initLifeTime(player)
        }

        player.showTitle(
            Title.title(
                Component.text(
                    localizer.translate(player.locale(), Messages.PLAYER_YOUR_TIME, lifetime)
                ),
                Component.empty()
            )
        )

        lifetimeTaskManager.startupTask(player)
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        if (config.isDisabled) {
            return
        }
        lifetimeTaskManager.cancelTask(event.player)
    }

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        if (config.isDisabled) {
            return
        }
        lifetimeTaskManager.startupTask(event.player)
    }

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        if (config.isDisabled) {
            return
        }

        val player = event.entity
        if (config.isUserExcluded(player.name)) {
            return
        }

        val killer = player.killer

        lifetimeTaskManager.cancelTask(player)

        if (killer == null) {
            val lifetime = lifetimeService.takeTimeFromPlayer(player)
            player.sendMessage(
                Component
                    .text(
                        localizer.translate(
                            player.locale(),
                            Messages.PLAYER_YOU_DIED_AND_LOST,
                            lifetime
                        )
                    )
                    .color(NamedTextColor.RED)
            )
            return
        }

        val lifetime = lifetimeService.transferLifetimeToAnotherPlayer(player, killer)
        player.sendMessage(
            Component
                .text(
                    localizer.translate(
                        player.locale(),
                        Messages.PLAYER_YOU_WERE_KILLED_AND_YOU_LOST,
                        killer.name,
                        lifetime
                    )
                )
                .color(NamedTextColor.RED)
        )

        killer.sendMessage(
            Component
                .text(
                    localizer.translate(
                        killer.locale(),
                        Messages.PLAYER_YOU_KILLED_AND_GOT,
                        player.name,
                        lifetime
                    )
                )
                .color(NamedTextColor.GREEN)
        )
    }

    @EventHandler
    fun onEntityDeathEvent(event: EntityDeathEvent) {
        if (config.isDisabled()) {
            return
        }

        val deader = event.entity

        val killer = deader.killer ?: return

        lifetimeService.getLifetimeAsReward(killer, deader)
            .ifPresent {
                killer.sendMessage(
                    Component
                        .text(
                            localizer.translate(
                                killer.locale(),
                                Messages.PLAYER_YOU_KILLED_AND_GOT,
                                deader.name,
                                it
                            )
                        )
                        .color(NamedTextColor.GREEN)
                )
            }
    }
}
