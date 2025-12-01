package dmitriy.losev.cs.dso.steam

import java.time.Instant

internal data class SteamAccountDSO(
    val steamId: Long,
    val login: String,
    val password: ByteArray,
    val sharedSecret: ByteArray,
    val identitySecret: ByteArray,
    val revocationCode: ByteArray,
    val deviceId: String,
    val createdAt: Instant
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SteamAccountDSO

        if (steamId != other.steamId) return false
        if (login != other.login) return false
        if (!password.contentEquals(other.password)) return false
        if (!sharedSecret.contentEquals(other.sharedSecret)) return false
        if (!identitySecret.contentEquals(other.identitySecret)) return false
        if (!revocationCode.contentEquals(other.revocationCode)) return false
        if (deviceId != other.deviceId) return false
        if (createdAt != other.createdAt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = steamId.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + sharedSecret.contentHashCode()
        result = 31 * result + identitySecret.contentHashCode()
        result = 31 * result + revocationCode.contentHashCode()
        result = 31 * result + deviceId.hashCode()
        result = 31 * result + createdAt.hashCode()
        return result
    }
}
