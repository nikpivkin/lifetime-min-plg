package io.github.nikpivkin.lifetime

import org.bukkit.entity.Player
import java.util.*

interface TaskIdHolder {

    fun get(player: Player): Optional<Int>

    fun put(player: Player, taskId: Int)

    fun remove(player: Player)
}
