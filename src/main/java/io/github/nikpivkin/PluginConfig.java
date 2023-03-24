package io.github.nikpivkin;

import io.github.nikpivkin.localize.Localizer;
import java.util.List;
import java.util.Set;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class PluginConfig {

  private final Plugin plugin;
  private final FileConfiguration configuration;
  private final Localizer localizer;

  private boolean enabled;
  private Lifetime lifetimeAtStart;
  private int lifetimeStartAfter;
  private float lifetimeLost;
  private OnTimeOutCallback onTimeOutCallback;
  private Long banTime;
  private int defaultReward;
  private Set<String> excludedUsers;

  public PluginConfig(Plugin plugin, Localizer localizer) {
    this.plugin = plugin;
    this.localizer = localizer;
    plugin.saveDefaultConfig();

    this.configuration = plugin.getConfig()
        .options()
        .copyDefaults(true)
        .configuration();

    initVariables();
  }

  public void reload() {
    plugin.reloadConfig();
    initVariables();
  }

  private void save() {
    plugin.saveConfig();
  }

  private void initVariables() {
    enabled = configuration.getBoolean("enabled", true);
    lifetimeAtStart = configuration.getObject(
        "lifetime.atStart", Lifetime.class, new Lifetime(600)
    );
    lifetimeStartAfter = configuration.getInt("lifetime.startAfter", 0);
    lifetimeLost = configuration.getObject(
        "lifetime.lost", Float.class, 0.5f
    );
    onTimeOutCallback = initOnTimeoutCallback(
        configuration.getString("lifetime.onOutCallback", "kick")
    );

    banTime = configuration.getObject("lifetime.banTime", Long.class, null);
    defaultReward = configuration.getInt("reward.default", 0);
    excludedUsers = Set.copyOf(configuration.getStringList("excluded"));
  }

  private OnTimeOutCallback initOnTimeoutCallback(String value) {
    return switch (value.toLowerCase()) {
      case "kick" -> new OnTimeOutCallback.Kick(localizer);
      case "ban" -> new OnTimeOutCallback.Ban(localizer, banTime);
      default -> throw new IllegalStateException("Unexpected value: " + value);
    };
  }

  public Lifetime lifetimeAtStart() {
    return this.lifetimeAtStart;
  }

  public int lifetimeStartAfter() {
    return this.lifetimeStartAfter;
  }

  public float lifetimeLost() {
    return this.lifetimeLost;
  }

  public OnTimeOutCallback onTimeOutCallback() {
    return this.onTimeOutCallback;
  }

  public int defaultReward() {
    return this.defaultReward;
  }

  public boolean isDisabled() {
    return !enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    configuration.set("enabled", enabled);
    save();
  }

  public boolean isUserExcluded(String name) {
    return excludedUsers.contains(name);
  }

  public void excludeUser(String name) {
    this.excludedUsers.add(name);
    configuration.set("excluded", List.copyOf(excludedUsers));
    save();
  }

  public void includeUser(String name) {
    this.excludedUsers.remove(name);
    configuration.set("excluded", List.copyOf(excludedUsers));
    save();
  }
}
