package io.github.nikpivkin.lifetime;

import io.github.nikpivkin.lifetime.localize.Localizer
import io.github.nikpivkin.lifetime.localize.Messages
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerKickEvent.Cause
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.*

sealed interface OnTimeOutCallback {

  fun apply(player: Player);

  class Kick (
      private val localizer: Localizer
  ): OnTimeOutCallback {

    override fun apply(player: Player) {
      player.kick(
          Component.text(
              localizer.translate(
                  player.locale(),
                  Messages.PLAYER_YOUR_TIME_IS_UP
              )
          ),
          Cause.PLUGIN
      );
    }
  }

  class Ban (
      private val localizer: Localizer,
      private val blockingTimeInSeconds: Optional<Long>
  ): OnTimeOutCallback {

    override fun apply(player: Player) {
      val expires = blockingTimeInSeconds
          .map { LocalDate.now().plus(it, ChronoUnit.SECONDS) }
          .map { Date.from(it.atStartOfDay(ZoneId.systemDefault()).toInstant()) }
          .orElse(null)

      player.banPlayerFull(
          localizer.translate(
              player.locale(),
              Messages.PLAYER_YOUR_TIME_IS_UP
          ),
          expires,
          null
      )
    }
  }
}
