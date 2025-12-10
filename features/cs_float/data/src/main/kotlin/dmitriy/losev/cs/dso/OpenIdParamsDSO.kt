package dmitriy.losev.cs.dso

data class OpenIdParamsDSO(
    val steamId: Long,
    val ns: String,
    val mode: String,
    val opEndpoint: String,
    val claimedId: String,
    val identity: String,
    val returnTo: String,
    val responseNonce: String,
    val assocHandle: String,
    val signed: String,
    val sig: String
) {

    fun convertToParameters(): Map<String, String> {
        return buildMap {
            put(key = NS_KEY, value = ns)
            put(key = MODE_KEY, value = mode)
            put(key = OP_ENDPOINT_KEY, value = opEndpoint)
            put(key = CLAIMED_ID_KEY, value = claimedId)
            put(key = IDENTITY_KEY, value = identity)
            put(key = RETURN_TO_KEY, value = returnTo)
            put(key = RESPONSE_NONCE_KEY, value = responseNonce)
            put(key = ASSOC_HANDLE_KEY, value = assocHandle)
            put(key = SIGNED_KEY, value = signed)
            put(key = SIG_KEY, value = sig)
        }
    }

    companion object {

        private const val NS_KEY = "openid.ns"
        private const val MODE_KEY = "openid.mode"
        private const val OP_ENDPOINT_KEY = "openid.op_endpoint"
        private const val CLAIMED_ID_KEY = "openid.claimed_id"
        private const val IDENTITY_KEY = "openid.identity"
        private const val RETURN_TO_KEY = "openid.return_to"
        private const val RESPONSE_NONCE_KEY = "openid.response_nonce"
        private const val ASSOC_HANDLE_KEY = "openid.assoc_handle"
        private const val SIGNED_KEY = "openid.signed"
        private const val SIG_KEY = "openid.sig"
    }
}
