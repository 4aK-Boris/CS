package dmitriy.losev.cs.cookie

data class NetworkCookie(
    val steamId: Long,
    val name: String,
    val value: String,
    val encoding: Int,
    val maxAge: Int?,
    val expires: Long?,
    val domain: String?,
    val path: String?,
    val secure: Boolean,
    val httpOnly: Boolean,
    val extensions: Map<String, String?> = emptyMap()
)
