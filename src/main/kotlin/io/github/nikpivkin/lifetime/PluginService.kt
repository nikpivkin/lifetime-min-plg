package io.github.nikpivkin.lifetime

import org.bukkit.entity.Player

public interface PluginService {

    fun enable()

    fun disable()

    fun excludeUser(player: Player)

    fun includeUser(player: Player)

    fun reload()
}
