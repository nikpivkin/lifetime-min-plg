package io.github.nikpivkin.lifetime.localize

import java.text.MessageFormat
import java.util.Locale
import java.util.Optional

interface LanguageStorage {
    fun loadAllLanguages(): Boolean

    fun getFormatterByLocaleAndKey(locale: Locale, key: String): Optional<MessageFormat>

    class Empty : LanguageStorage {
        override fun loadAllLanguages() = true

        override fun getFormatterByLocaleAndKey(
            locale: Locale,
            key: String
        ): Optional<MessageFormat> = Optional.empty()
    }
}
