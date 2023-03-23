package io.github.nikpivkin.commands;

import io.github.nikpivkin.LifetimeService;
import io.github.nikpivkin.PluginConfig;
import io.github.nikpivkin.PluginService;
import java.util.List;
import java.util.function.Predicate;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class LifetimeCommandExecutor implements CommandExecutor {

  public static final String NAME = "lifetime";

  private final PluginConfig config;
  private final LifetimeService lifetimeService;
  private final PluginService pluginService;
  private final Plugin plugin;
  private final List<io.github.nikpivkin.commands.Command> commands;

  public LifetimeCommandExecutor(
      PluginConfig config,
      LifetimeService lifetimeService,
      PluginService pluginService,
      Plugin plugin
  ) {
    this.config = config;
    this.lifetimeService = lifetimeService;
    this.pluginService = pluginService;
    this.plugin = plugin;
    this.commands = initCommands();
  }

  private List<io.github.nikpivkin.commands.Command> initCommands() {
    return List.of(
        new UsageCommand(),
        new ReloadCommand(pluginService),
        new SetLifetimeCommand(lifetimeService),
        new AddLifetimeCommand(lifetimeService),
        new DisablePluginCommand(pluginService),
        new EnablePluginCommand(pluginService),
        new GetLifetimeCommand(lifetimeService),
        new GodModeCommand(pluginService, plugin)
    );
  }

  @Override
  public boolean onCommand(
      @NotNull CommandSender sender,
      @NotNull Command command,
      @NotNull String label,
      @NotNull String[] args
  ) {
    if (args.length == 0) {
      return new UsageCommand().execute(sender, command, label, args);
    }

    var firstCommand = cmd(args[0]);

    return commands.stream()
        .filter(cmd -> firstCommand.test(cmd.name()))
        .findFirst()
        .map(cmd -> cmd.execute(sender, command, label, args))
        .orElseGet(() -> {
          sender.sendMessage(ChatColor.RED + "Unknown command");
          return new UsageCommand().execute(sender, command, label, args);
        });
  }

  private Predicate<String> cmd(String command) {
    return command::equalsIgnoreCase;
  }
}
