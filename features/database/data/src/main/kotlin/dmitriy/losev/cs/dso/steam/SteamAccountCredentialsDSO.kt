package dmitriy.losev.cs.dso.steam

data class SteamAccountCredentialsDSO(
    val steamId: Long,
    val login: String,
    val password: ByteArray,
    val sharedSecret: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SteamAccountCredentialsDSO

        if (steamId != other.steamId) return false
        if (login != other.login) return false
        if (!password.contentEquals(other.password)) return false
        if (!sharedSecret.contentEquals(other.sharedSecret)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = steamId.hashCode()
        result = 31 * result + login.hashCode()
        result = 31 * result + password.contentHashCode()
        result = 31 * result + sharedSecret.contentHashCode()
        return result
    }
}
