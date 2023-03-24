package io.github.nikpivkin.localize;

public class Messages {

  public static final String COMMAND_ADD_INVALID_AMOUNT = "command.add.invalid.amount";
  public static final String PLAYER_HAS_LIFETIME = "player.has.lifetime";
  public static final String PLAYER_YOUR_TIME = "player.your.time";
  public static final String PLAYER_YOU_DIED_AND_LOST = "player.you.died.and.lost";
  public static final String PLAYER_YOU_WERE_KILLED_AND_YOU_LOST = "player.you.were.killed.and.you.lost";
  public static final String PLAYER_YOU_KILLED_AND_GOT = "player.you.killed.and.got";
  public static final String PLAYER_YOUR_TIME_IS_UP = "player.your.time.is.up";
  public static final String COMMAND_ADD_USAGE = "Usage `add` command: /lifetime add <username> <amount>";

  // ---------
  public static final String COMMAND_DISABLE_OK = "Lifetime plugin has disabled";
  public static final String COMMAND_ENABLE_OK = "Lifetime plugin has enabled";
  public static final String COMMAND_GET_USAGE = "Usage `get` command: /lifetime get <username>";
  public static final String COMMAND_GOD_MODE_USAGE = "Usage `god` command: /lifetime god enable|disable <username> <time>?";
  public static final String COMMAND_GOD_MODE_INVALID_EXPIRES = "Invalid expires format";
  public static final String COMMAND_GOD_MODE_FAILED_SCHEDULE_TASK = "failed to schedule a command cancellation task";
  public static final String COMMAND_RELOAD_OK = "Config reloaded";
  public static final String COMMAND_SET_USAGE = "Usage `set` command: /lifetime set <username> <amount>";
  public static final String COMMAND_SET_INVALID_AMOUNT = "invalid amount";
  public static final String COMMAND_SET_LIFETIME_TO = "The lifetime of the %s player is set to %s";
  public static final String COMMAND_USAGE_USAGE = "Usage...";
  public static final String INVALID_SUBCOMMAND = "Invalid subcommand";
  public static final String UNKNOWN_COMMAND = "Unknown command";
  public static final String PLAYER_NOT_FOUND = "Player with name %s not found";

  // ---------
  public static final String COMMAND_GOD_MODE_ENABLED = "God mode is enabled for the player %s";
  public static final String COMMAND_GOD_MODE_DISABLED = "God mode is disabled for the player %s";
  public static final String COMMAND_GOD_MODE_EXPIRED = "The time of the god mode for the %s has expired";
  private Messages() {
    // companion object
  }
}
