package io.github.nikpivkin;

import io.github.nikpivkin.commands.LifetimeCommandExecutor;
import io.github.nikpivkin.impl.LifetimeHolderInDataContainer;
import io.github.nikpivkin.impl.LifetimeServiceImpl;
import io.github.nikpivkin.impl.LifetimeTaskManagerImpl;
import io.github.nikpivkin.impl.PluginServiceImpl;
import io.github.nikpivkin.impl.TaskIdHolderInDataContainer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class LifetimePlugin extends JavaPlugin {

  private final NamespacedKey lifetimeKey = new NamespacedKey(this, "lifetime");
  private final NamespacedKey taskIdKey = new NamespacedKey(this, "taskId");

  private LifetimeTaskManager taskManager;

  @Override
  public void onEnable() {
    var config = PluginConfig.create(this);
    var lifetimeHolder = new LifetimeHolderInDataContainer(lifetimeKey);
    var lifetimeService = new LifetimeServiceImpl(config, lifetimeHolder);
    var pluginService = new PluginServiceImpl(config);
    var taskIdHolder = new TaskIdHolderInDataContainer(taskIdKey);
    this.taskManager = new LifetimeTaskManagerImpl(config, lifetimeService, this, taskIdHolder);

    var lifetimeCommand = getCommand(LifetimeCommandExecutor.NAME);
    if (lifetimeCommand != null) {
      lifetimeCommand.setExecutor(
          new LifetimeCommandExecutor(
              config, lifetimeService,
              pluginService, this
          )
      );
    }

    var lifetimeListener = new LifetimeListener(lifetimeService, taskManager, config);
    Bukkit.getPluginManager().registerEvents(lifetimeListener, this);
    Bukkit.getPluginManager().registerEvents(new LifetimeInternalListener(taskManager), this);

    taskManager.startupTask(Bukkit.getOnlinePlayers());
  }

  @Override
  public void onDisable() {
    taskManager.cancelTask(Bukkit.getOnlinePlayers());
    Bukkit.getOnlinePlayers().forEach(taskManager::cancelTask);
  }
}
