package dmitriy.losev.cs.dso.auth.openid

data class PostRequestOpenIdParamsDSO(
    val steamId: Long,
    val boundary: String,
    val formText: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostRequestOpenIdParamsDSO

        if (steamId != other.steamId) return false
        if (boundary != other.boundary) return false
        if (!formText.contentEquals(other.formText)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = steamId.hashCode()
        result = 31 * result + boundary.hashCode()
        result = 31 * result + formText.contentHashCode()
        return result
    }
}
