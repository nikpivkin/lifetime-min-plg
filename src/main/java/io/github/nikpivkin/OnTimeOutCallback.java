package io.github.nikpivkin;

import io.github.nikpivkin.localize.Localizer;
import io.github.nikpivkin.localize.Messages;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent.Cause;

public sealed interface OnTimeOutCallback {

  void apply(Player player);

  final class Kick implements OnTimeOutCallback {

    private final Localizer localizer;

    public Kick(Localizer localizer) {
      this.localizer = localizer;
    }

    @Override
    public void apply(Player player) {
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

  final class Ban implements OnTimeOutCallback {

    private final Localizer localizer;
    private final Optional<Long> blockingTimeInSeconds;

    public Ban(Localizer localizer, Long blockingTimeInSeconds) {
      this.localizer = localizer;
      this.blockingTimeInSeconds = Optional.ofNullable(blockingTimeInSeconds);
    }

    @Override
    public void apply(Player player) {
      var expires = blockingTimeInSeconds.map(
          e -> LocalDate.now().plus(e, ChronoUnit.SECONDS)
      ).map(date -> Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

      player.banPlayerFull(
          localizer.translate(
              player.locale(),
              Messages.PLAYER_YOUR_TIME_IS_UP
          ),
          expires.orElse(null), null)
      ;
    }
  }
}
