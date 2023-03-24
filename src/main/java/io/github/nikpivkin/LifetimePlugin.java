package io.github.nikpivkin;

import io.github.nikpivkin.commands.LifetimeCommandExecutor;
import io.github.nikpivkin.impl.LifetimeHolderInDataContainer;
import io.github.nikpivkin.impl.LifetimeServiceImpl;
import io.github.nikpivkin.impl.LifetimeTaskManagerImpl;
import io.github.nikpivkin.impl.PluginServiceImpl;
import io.github.nikpivkin.impl.TaskIdHolderInDataContainer;
import io.github.nikpivkin.localize.LanguageStorage;
import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.impl.LanguageStorageImpl;
import io.github.nikpivkin.localize.impl.LocalizerImpl;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

public final class LifetimePlugin extends JavaPlugin {

  private final NamespacedKey lifetimeKey = new NamespacedKey(this, "lifetime");
  private final NamespacedKey taskIdKey = new NamespacedKey(this, "taskId");

  private @Nullable LifetimeTaskManager taskManager;

  @Override
  public void onEnable() {

    copyTranslations();
    var localizer = initLocalizer();

    var config = new PluginConfig(this, localizer);
    var lifetimeHolder = new LifetimeHolderInDataContainer(lifetimeKey);
    var lifetimeService = new LifetimeServiceImpl(config, lifetimeHolder);
    var pluginService = new PluginServiceImpl(config);
    var taskIdHolder = new TaskIdHolderInDataContainer(taskIdKey);
    this.taskManager = new LifetimeTaskManagerImpl(config, lifetimeService, this, taskIdHolder);

    var lifetimeCommand = getCommand(LifetimeCommandExecutor.NAME);
    if (lifetimeCommand != null) {
      lifetimeCommand.setExecutor(
          new LifetimeCommandExecutor(
              lifetimeService,
              pluginService,
              this,
              localizer
          )
      );
    }

    var lifetimeListener = new LifetimeListener(lifetimeService, taskManager, config, localizer);
    Bukkit.getPluginManager().registerEvents(lifetimeListener, this);
    Bukkit.getPluginManager().registerEvents(new LifetimeInternalListener(taskManager), this);

    taskManager.startupTask(Bukkit.getOnlinePlayers());
  }

  private void copyTranslations() {
    var maybeError = ResourceUtil.listResourcesInJar(
        jarEntry -> {
          var path = jarEntry.getName();
          if (path.startsWith("i18n/") && !"i18n/".equals(path)) {
            saveResource(path, true);
          }
        }
    );

    maybeError.ifPresent(e -> getLogger().severe(e.getMessage()));
  }

  private Localizer initLocalizer() {
    var translationsDirectory = new File(getDataFolder(), "i18n");
    if (!translationsDirectory.exists()) {
      return new LocalizerImpl(LanguageStorage.EMPTY);
    }
    var languageStorage = new LanguageStorageImpl(translationsDirectory);
    if (!languageStorage.loadAllLanguages()) {
      getLogger().severe("Failed to load resources with translations");
      return new LocalizerImpl(LanguageStorage.EMPTY);
    }

    return new LocalizerImpl(languageStorage);
  }

  @Override
  public void onDisable() {
    if (taskManager != null) {
      taskManager.cancelTask(Bukkit.getOnlinePlayers());
    }
  }
}
