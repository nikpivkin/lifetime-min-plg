package io.github.nikpivkin.lifetime

import org.bukkit.entity.Player

interface LifetimeTaskManager {
    fun startupTask(player: Player)
    fun startupTask(players: Collection<Player>)
    fun cancelTask(player: Player)
    fun cancelTask(players: Collection<Player>)

    companion object {
        const val ONE_SECOND_IN_TICKS = 20L
    }
}
