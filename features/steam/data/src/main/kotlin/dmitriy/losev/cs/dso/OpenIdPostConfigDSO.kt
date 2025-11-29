package dmitriy.losev.cs.dso

data class OpenIdPostConfigDSO(
    val action: String,
    val mode: String,
    val openIdParams: String,
    val nonce: String
) {

    fun convertToParameters(): Map<String, String> {
        return buildMap {
            put(key = ACTION_KEY, value = action)
            put(key = MODE_KEY, value = mode)
            put(key = OPEN_ID_PARAMS_KEY, value = openIdParams)
            put(key = NONCE_KEY, value = nonce)
        }
    }

    companion object {

        private const val ACTION_KEY = "action"
        private const val MODE_KEY = "openid.mode"
        private const val OPEN_ID_PARAMS_KEY = "openidparams"
        private const val NONCE_KEY = "nonce"
    }
}
