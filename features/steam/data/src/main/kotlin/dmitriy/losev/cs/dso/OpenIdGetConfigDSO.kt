package dmitriy.losev.cs.dso

data class OpenIdGetConfigDSO(
    val claimedId: String,
    val identity: String,
    val mode: String,
    val ns: String,
    val realm: String,
    val returnTo: String
) {

    fun convertToParameters(): Map<String, String> {
        return buildMap {
            put(key = CLAIMED_ID_KEY, value = claimedId)
            put(key = IDENTITY_KEY, value = identity)
            put(key = MODE_KEY, value = mode)
            put(key = NS_KEY, value = ns)
            put(key = REALM_KEY, value = realm)
            put(key = RETURN_TO_KEY, value = returnTo)
        }
    }

    companion object {

        private const val CLAIMED_ID_KEY = "openid.claimed_id"
        private const val IDENTITY_KEY = "openid.identity"
        private const val MODE_KEY = "openid.mode"
        private const val NS_KEY = "openid.ns"
        private const val REALM_KEY = "openid.realm"
        private const val RETURN_TO_KEY = "openid.return_to"
    }
}
