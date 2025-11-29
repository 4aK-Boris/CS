package dmitriy.losev.cs.core

data class RandomBrowserProfile(
    val userAgent: String,
    val acceptLanguage: String,
    val primaryLang: String,
    val timezone: String,
    val platform: String,
    val navigatorPlatform: String,
    val viewport: Pair<Int, Int>,
    val deviceScaleFactor: Double,
    val hardwareConcurrency: Int,
    val deviceMemory: Int
)