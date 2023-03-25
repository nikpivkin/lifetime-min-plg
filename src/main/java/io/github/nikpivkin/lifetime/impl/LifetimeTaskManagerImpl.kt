package io.github.nikpivkin.lifetime.impl

import io.github.nikpivkin.lifetime.LifetimeService
import io.github.nikpivkin.lifetime.LifetimeTaskManager
import io.github.nikpivkin.lifetime.PluginConfig
import io.github.nikpivkin.lifetime.StartupLifetimeTask
import io.github.nikpivkin.lifetime.TaskIdHolder
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class LifetimeTaskManagerImpl(
    private val config: PluginConfig,
    private val lifetimeService: LifetimeService,
    private val plugin: Plugin,
    private val taskIdHolder: TaskIdHolder
) : LifetimeTaskManager {

    override fun startupTask(player: Player) {
        if (config.isUserExcluded(player.name)) {
            return
        }

        val task = StartupLifetimeTask(lifetimeService, config, player)
            .runTaskTimer(plugin, config.lifetimeStartAfter().toLong(), LifetimeTaskManager.ONE_SECOND_IN_TICKS)

        taskIdHolder.put(player, task.taskId)
    }

    override fun startupTask(players: Collection<Player>) = players.forEach { startupTask(it) }

    override fun cancelTask(player: Player) {
        if (config.isUserExcluded(player.name)) {
            return
        }

        taskIdHolder.get(player)
            .ifPresent {
                Bukkit.getScheduler().cancelTask(it)
                taskIdHolder.remove(player)
            }
    }

    override fun cancelTask(players: Collection<Player>) = players.forEach { cancelTask(it) }
}
