package io.github.nikpivkin.lifetime.impl;

import io.github.nikpivkin.lifetime.TaskIdHolder;
import java.util.Optional;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

public class TaskIdHolderInDataContainer implements TaskIdHolder {

  private final NamespacedKey taskIdKey;

  public TaskIdHolderInDataContainer(NamespacedKey taskIdKey) {
    this.taskIdKey = taskIdKey;
  }

  @Override
  public Optional<Integer> get(Player player) {
    var dataContainer = player.getPersistentDataContainer();
    var taskId = dataContainer.get(taskIdKey, PersistentDataType.INTEGER);
    return Optional.ofNullable(taskId);
  }

  @Override
  public void put(Player player, int taskId) {
    player.getPersistentDataContainer()
        .set(taskIdKey, PersistentDataType.INTEGER, taskId);
  }

  @Override
  public void remove(Player player) {
    player.getPersistentDataContainer().remove(taskIdKey);
  }
}
