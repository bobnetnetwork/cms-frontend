package network.bobnet.cms.util

import java.text.Normalizer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

class Extensions{
    fun LocalDateTime.format(): String = this.format(englishDateFormatter)

    private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

    private val englishDateFormatter = DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .appendLiteral(" ")
            .appendText(ChronoField.DAY_OF_MONTH, daysLookup)
            .appendLiteral(" ")
            .appendPattern("yyyy")
            .toFormatter(Locale.ENGLISH)

    private fun getOrdinal(n: Int) = when {
        n in 11..13 -> "${n}th"
        n % 10 == 1 -> "${n}st"
        n % 10 == 2 -> "${n}nd"
        n % 10 == 3 -> "${n}rd"
        else -> "${n}th"
    }

    fun String.toSlug() = toLowerCase()
            .replace("\n", " ")
            .replace("[^a-z\\d\\s]".toRegex(), " ")
            .split(" ")
            .joinToString("-")
            .replace("-+".toRegex(), "-")

    fun slugify(word: String, replacement: String = "-") = Normalizer
            .normalize(word, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
            .replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim()
            .replace("\\s+".toRegex(), replacement)
            .toLowerCase()

    fun slugifyCode(word: String, replacement: String = "_") = Normalizer
            .normalize(word, Normalizer.Form.NFD)
            .replace("[^\\p{ASCII}]".toRegex(), "")
            .replace("[^a-zA-Z0-9\\s]+".toRegex(), "").trim()
            .replace("\\s+".toRegex(), replacement)
            .toLowerCase()

}
