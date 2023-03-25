package io.github.nikpivkin.lifetime.localize.impl

import io.github.nikpivkin.lifetime.localize.LanguageStorage
import io.github.nikpivkin.lifetime.localize.Localizer
import java.util.Locale

class LocalizerImpl(
    private val storage: LanguageStorage
) : Localizer {

    override fun translate(locale: Locale, key: String, vararg args: Any): String {
        return storage.getFormatterByLocaleAndKey(locale, key)
            .map { it.format(args) }
            .orElse("Key $key not found")
    }
}
