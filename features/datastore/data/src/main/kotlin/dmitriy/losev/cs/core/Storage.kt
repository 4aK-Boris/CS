package dmitriy.losev.cs.core

class Storage(dataStoreInstance: DataStoreInstance) {

    var pulseAuthToken by dataStoreInstance.preference<String>(key = PULSE_AUTH_TOKEN_KEY, defaultValue = DEFAULT_STRING)

    var pulseAuthTokenLastUpdate by dataStoreInstance.preference<Long>(key = PULSE_AUTH_TOKEN_LAST_UPDATE_KEY, defaultValue = 0)

    companion object {

        private const val DEFAULT_STRING = ""

        private const val PULSE_AUTH_TOKEN_KEY = "pulse_auth_token"
        private const val PULSE_AUTH_TOKEN_LAST_UPDATE_KEY = "pulse_auth_token_last_update"
    }
}