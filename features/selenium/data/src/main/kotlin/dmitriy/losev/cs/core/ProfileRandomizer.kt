package dmitriy.losev.cs.core

import java.security.SecureRandom
import kotlin.math.max

object ProfileRandomizer {

    private val rnd = SecureRandom()

    private val chromeVersions = listOf(
        "126.0.0.0", "127.0.6533.72", "128.0.6613.86", "129.0.6668.58"
    )
    private val platforms = listOf(
        "Windows" to "Win32",
        "Mac" to "MacIntel",
        "Linux" to "Linux x86_64"
    )
    private val timezones = listOf(
        "Europe/Helsinki","Europe/Stockholm","Europe/Berlin","Europe/Warsaw",
        "Europe/Riga","Europe/Vilnius","Europe/Kiev","Europe/Moscow"
    )
    private val langSets = listOf(
        "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7",
        "en-US,en;q=0.9,ru;q=0.6",
        "ru,ru-RU;q=0.9,en;q=0.7"
    )
    private val primaryLang = listOf("ru-RU", "en-US", "ru")
    private val viewports = listOf( // популярные desktop-разрешения
        1920 to 1080, 1536 to 864, 1366 to 768, 1600 to 900, 1440 to 900
    )
    private fun pick(n: Int) = rnd.nextInt(n)

    fun random(): RandomBrowserProfile {
        val (osKey, navPlatform) = platforms[pick(platforms.size)]
        val chrome = chromeVersions[pick(chromeVersions.size)]
        val tz = timezones[pick(timezones.size)]
        val langs = langSets[pick(langSets.size)]
        val langPrimary = primaryLang[pick(primaryLang.size)]
        val (w, h) = viewports[pick(viewports.size)]
        val dpr = listOf(1.0, 1.25, 1.5, 1.75, 2.0)[pick(n = 5)]
        val cores = max(2, 2 + rnd.nextInt(14))
        val mem = listOf(4, 6, 8, 12, 16)[pick(5)]

        val userAgent = when (osKey) {
            "Windows" -> "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/$chrome Safari/537.36"
            "Mac" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) " +
                    "AppleWebKit/605.1.15 (KHTML, like Gecko) " +
                    "Chrome/$chrome Safari/537.36"
            else -> "Mozilla/5.0 (X11; Linux x86_64) " +
                    "AppleWebKit/537.36 (KHTML, like Gecko) " +
                    "Chrome/$chrome Safari/537.36"
        }

        val platform = if (osKey == "Windows") "Windows" else osKey

        return RandomBrowserProfile(
            userAgent = userAgent,
            acceptLanguage = langs,
            primaryLang = langPrimary,
            timezone = tz,
            platform = platform,
            navigatorPlatform = navPlatform,
            viewport = w to h,
            deviceScaleFactor = dpr,
            hardwareConcurrency = cores,
            deviceMemory = mem
        )
    }
}