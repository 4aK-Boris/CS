package dmitriy.losev.ozon.core

import dmitriy.losev.ozon.dso.InspectParamsDSO
import java.net.URLDecoder
import org.koin.core.annotation.Factory

@Factory
class InspectLinkParser {

    private val sRegex = Regex(pattern = S_PATTERN_REGEX)
    private val aRegex = Regex(pattern = A_PATTERN_REGEX)
    private val mRegex = Regex(pattern = M_PATTERN_REGEX)
    private val dRegex = Regex(pattern = D_PATTERN_REGEX)

    fun parse(link: String): InspectParamsDSO? {

        val decodedLink = URLDecoder.decode(link, "UTF-8")

        val sMatch = sRegex.find(input = decodedLink)
        val aMatch = aRegex.find(input = decodedLink)
        val dMatch = dRegex.find(input = decodedLink)
        val mMatch = mRegex.find(input = decodedLink)

        val s = sMatch?.groupValues?.get(1)?.toULongOrNull() ?: 0UL
        val a = aMatch?.groupValues?.get(1)?.toULongOrNull() ?: return null
        val d = dMatch?.groupValues?.get(1)?.toULongOrNull() ?: 0UL
        val m = mMatch?.groupValues?.get(1)?.toULongOrNull() ?: 0UL

        return InspectParamsDSO(s, a, d, m)
    }

    companion object {
        // Используем lookahead (?=[A-Za-z]|$) чтобы остановиться на следующей букве или конце строки
        private const val S_PATTERN_REGEX = "[sS](\\d+)(?=[A-Za-z]|$)"
        private const val A_PATTERN_REGEX = "[aA](\\d+)(?=[A-Za-z]|$)"
        private const val M_PATTERN_REGEX = "[mM](\\d+)(?=[A-Za-z]|$)"
        private const val D_PATTERN_REGEX = "[dD](\\d+)(?=[A-Za-z]|$)"
    }
}