package io.github.nikpivkin.lifetime.localize;

import java.util.Locale;

interface Localizer {

  fun translate(locale: Locale, key: String, vararg args: Any): String
}
