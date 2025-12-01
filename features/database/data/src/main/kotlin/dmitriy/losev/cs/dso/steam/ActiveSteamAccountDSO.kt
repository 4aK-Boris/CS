package dmitriy.losev.cs.dso.steam

import java.time.Instant

internal data class ActiveSteamAccountDSO(
    val steamId: Long,
    val marketCSGOApiToken: ByteArray,
    val accessToken: String,
    val refreshToken: ByteArray,
    val sessionId: String,
    val createdAt: Instant
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ActiveSteamAccountDSO

        if (steamId != other.steamId) return false
        if (!marketCSGOApiToken.contentEquals(other.marketCSGOApiToken)) return false
        if (accessToken != other.accessToken) return false
        if (!refreshToken.contentEquals(other.refreshToken)) return false
        if (sessionId != other.sessionId) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = steamId.hashCode()
        result = 31 * result + marketCSGOApiToken.contentHashCode()
        result = 31 * result + accessToken.hashCode()
        result = 31 * result + refreshToken.contentHashCode()
        result = 31 * result + sessionId.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }

}
