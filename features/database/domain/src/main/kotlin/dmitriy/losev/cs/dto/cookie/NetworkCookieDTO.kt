package dmitriy.losev.cs.dto.cookie

import java.time.Instant

data class NetworkCookieDTO(
    val steamId: Long,
    val name: String,
    val value: String,
    val encoding: Int = 2,
    val maxAge: Int?,
    val expires: Instant?,
    val domain: String?,
    val path: String?,
    val secure: Boolean = false,
    val httpOnly: Boolean = false,
    val extensions: Map<String, String?> = emptyMap()
)
