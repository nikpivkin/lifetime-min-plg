package io.github.nikpivkin;

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

    @Override
    public void apply(Player player) {
      player.kick(
          Component.text("Твое время вышло =)"),
          Cause.PLUGIN
      );
    }
  }

  final class Ban implements OnTimeOutCallback {

    private final Optional<Long> blockingTimeInSeconds;

    public Ban(Long blockingTimeInSeconds) {
      this.blockingTimeInSeconds = Optional.ofNullable(blockingTimeInSeconds);
    }

    @Override
    public void apply(Player player) {
      var expires = blockingTimeInSeconds.map(
          e -> LocalDate.now().plus(e, ChronoUnit.SECONDS)
      ).map(date -> Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()));

      player.banPlayerFull("Твое время вышло =)", expires.orElse(null), null);
    }
  }
}
