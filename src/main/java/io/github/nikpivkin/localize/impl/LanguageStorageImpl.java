package io.github.nikpivkin.localize.impl;

import io.github.nikpivkin.localize.LanguageStorage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import org.bukkit.Bukkit;

public class LanguageStorageImpl implements LanguageStorage {

  private final Map<Locale, Map<String, MessageFormat>> localeFormatters = new HashMap<>();
  private final File baseDir;

  public LanguageStorageImpl(File file) {
    baseDir = file;
  }

  public boolean loadAllLanguages() {
    var files = baseDir.listFiles((dir, name) -> name.endsWith(".properties"));
    if (files == null) {
      return false;
    }
    for (var file : files) {
      var formatters = new HashMap<String, MessageFormat>();
      try (var fis = new FileInputStream(file)) {
        var br = new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8));
        var properties = new Properties();
        properties.load(br);
        var enumerator = properties.propertyNames();
        while (enumerator.hasMoreElements()) {
          String propertyName = (String) enumerator.nextElement();
          formatters.put(propertyName, new MessageFormat(properties.getProperty(propertyName)));
        }
      } catch (IOException e) {
        return false;
      }
      var fileName = file.getName().substring(0, file.getName().indexOf("."));
      localeFormatters.put(new Locale(fileName), formatters);
    }

    return true;
  }

  @Override
  public Optional<MessageFormat> getFormatterByLocaleAndKey(Locale locale, String key) {
    return getFormatters(locale).map(m -> m.get(key));
  }

  @Override
  public Optional<Map<String, MessageFormat>> getFormatters(Locale locale) {
    var formatters = localeFormatters.get(locale);
    return Optional.ofNullable(formatters);
  }
}
