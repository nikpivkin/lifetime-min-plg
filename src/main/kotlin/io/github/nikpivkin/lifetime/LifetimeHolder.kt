package io.github.nikpivkin.lifetime

import org.bukkit.entity.Player
import java.util.Optional

interface LifetimeHolder {
    fun put(player: Player, lifetime: Lifetime)
    fun get(player: Player): Optional<Lifetime>
}
