package io.github.nikpivkin.lifetime.localize;

import java.util.Locale;

public interface Localizer {

  String translate(Locale locale, String key, Object... args);

  VarArgsBiFunction<String, Object, String> translate(Locale locale);

  @FunctionalInterface
  interface VarArgsBiFunction<T, U, R> {

    R apply(T key, U... args);
  }
}
