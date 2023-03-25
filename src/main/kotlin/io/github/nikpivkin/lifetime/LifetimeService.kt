package io.github.nikpivkin.lifetime

import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import java.util.*

interface LifetimeService {
    fun initLifeTime(player: Player): Lifetime

    fun increaseLifeTime(player: Player, lifetime: Lifetime): Lifetime

    fun decrementLifetime(player: Player): Lifetime

    fun setLifetime(player: Player, lifetime: Lifetime): Lifetime

    fun getLifeTime(player: Player): Lifetime

    fun takeTimeFromPlayer(player: Player): Lifetime

    fun takeTimeFromPlayer(player: Player, percentage: Float): Lifetime

    fun transferLifetimeToAnotherPlayer(from: Player, to: Player): Lifetime

    fun getLifetimeAsReward(killer: Player, deadEntity: Entity): Optional<Lifetime>
}
