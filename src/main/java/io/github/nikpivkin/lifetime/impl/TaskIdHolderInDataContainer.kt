package io.github.nikpivkin.lifetime.impl

import io.github.nikpivkin.lifetime.TaskIdHolder
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType
import java.util.Optional

public class TaskIdHolderInDataContainer(
    private val taskIdKey: NamespacedKey
) : TaskIdHolder {

    override fun get(player: Player): Optional<Int> {
        val dataContainer = player.persistentDataContainer
        val taskId = dataContainer.get(taskIdKey, PersistentDataType.INTEGER)
        return Optional.ofNullable(taskId)
    }

    override fun put(player: Player, taskId: Int) {
        player.persistentDataContainer
            .set(taskIdKey, PersistentDataType.INTEGER, taskId)
    }

    override fun remove(player: Player) {
        player.persistentDataContainer.remove(taskIdKey)
    }
}
