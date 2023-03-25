package io.github.nikpivkin.lifetime.localize

object Messages {

    const val COMMAND_ADD_INVALID_AMOUNT = "command.add.invalid.amount"
    const val PLAYER_HAS_LIFETIME = "player.has.lifetime"
    const val PLAYER_YOUR_TIME = "player.your.time"
    const val PLAYER_YOU_DIED_AND_LOST = "player.you.died.and.lost"
    const val PLAYER_YOU_WERE_KILLED_AND_YOU_LOST = "player.you.were.killed.and.you.lost"
    const val PLAYER_YOU_KILLED_AND_GOT = "player.you.killed.and.got"
    const val PLAYER_YOUR_TIME_IS_UP = "player.your.time.is.up"
    const val COMMAND_ADD_USAGE = "Usage `add` command: /lifetime add <username> <amount>"

    // ---------
    const val COMMAND_DISABLE_OK = "Lifetime plugin has disabled"
    const val COMMAND_ENABLE_OK = "Lifetime plugin has enabled"
    const val COMMAND_GET_USAGE = "Usage `get` command: /lifetime get <username>"
    const val COMMAND_GOD_MODE_USAGE = "Usage `god` command: /lifetime god enable|disable <username> <time>?"
    const val COMMAND_GOD_MODE_INVALID_EXPIRES = "Invalid expires format"
    const val COMMAND_GOD_MODE_FAILED_SCHEDULE_TASK = "failed to schedule a command cancellation task"
    const val COMMAND_RELOAD_OK = "Config reloaded"
    const val COMMAND_SET_USAGE = "Usage `set` command: /lifetime set <username> <amount>"
    const val COMMAND_SET_INVALID_AMOUNT = "invalid amount"
    const val COMMAND_SET_LIFETIME_TO = "The lifetime of the %s player is set to %s"
    const val COMMAND_USAGE_USAGE = "Usage..."
    const val INVALID_SUBCOMMAND = "Invalid subcommand"
    const val UNKNOWN_COMMAND = "Unknown command"
    const val PLAYER_NOT_FOUND = "Player with name %s not found"

    // ---------
    const val COMMAND_GOD_MODE_ENABLED = "God mode is enabled for the player %s"
    const val COMMAND_GOD_MODE_DISABLED = "God mode is disabled for the player %s"
    const val COMMAND_GOD_MODE_EXPIRED = "The time of the god mode for the %s has expired"
}
