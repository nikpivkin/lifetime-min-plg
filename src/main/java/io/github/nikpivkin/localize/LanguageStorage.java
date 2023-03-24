package io.github.nikpivkin.localize;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

public interface LanguageStorage {

  LanguageStorage EMPTY = new LanguageStorage() {
    @Override
    public boolean loadAllLanguages() {
      return true;
    }

    @Override
    public Optional<MessageFormat> getFormatterByLocaleAndKey(Locale locale, String key) {
      return Optional.empty();
    }

    @Override
    public Optional<Map<String, MessageFormat>> getFormatters(Locale locale) {
      return Optional.empty();
    }
  };

  boolean loadAllLanguages();

  Optional<MessageFormat> getFormatterByLocaleAndKey(Locale locale, String key);

  Optional<Map<String, MessageFormat>> getFormatters(Locale locale);

}
