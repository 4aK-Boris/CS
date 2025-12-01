package dmitriy.losev.cs

data class HttpLoggingConfig(
    val enabled: Boolean = true,
    val level: String = "DEBUG",
    val excludedPaths: List<String> = emptyList(),
    val sensitiveFields: List<String> = emptyList(),
    val sensitiveHeaders: List<String> = emptyList(),
    val maxBodySize: Int = 10000
)
