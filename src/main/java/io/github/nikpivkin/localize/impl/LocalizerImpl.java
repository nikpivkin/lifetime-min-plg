package io.github.nikpivkin.localize.impl;

import io.github.nikpivkin.localize.LanguageStorage;
import io.github.nikpivkin.localize.Localizer;
import java.util.Locale;

public class LocalizerImpl implements Localizer {

  private final LanguageStorage storage;

  public LocalizerImpl(LanguageStorage storage) {
    this.storage = storage;
  }

  @Override
  public String translate(Locale locale, String key, Object... args) {
    return storage.getFormatterByLocaleAndKey(locale, key)
        .map(formatter -> formatter.format(args))
        .orElse("Key " + key + " not found")
        ;
  }

  @Override
  public VarArgsBiFunction<String, Object, String> translate(Locale locale) {
    var formatters = storage.getFormatters(locale);
    return (key, args) -> formatters
        .map(m -> m.get(key))
        .map(f -> f.format(args))
        .orElse("Key " + key + " not found");
  }
}
