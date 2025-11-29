package dmitriy.losev.cs.exceptions

//0-999
sealed class NetworkBaseException(errorCode: Int, message: String): BaseException(errorCode, message) {

    class NetworkException(message: String): NetworkBaseException(
        errorCode = NETWORK_EXCEPTION_CODE,
        message = message
    )

    class SteamAccountProxyIsNotExistsException(steamId: ULong): NetworkBaseException(
        errorCode = STEAM_ACCOUNT_PROXY_IS_NOT_EXISTS_EXCEPTION_CODE,
        message = STEAM_ACCOUNT_PROXY_IS_NOT_EXISTS_EXCEPTION_MESSAGE.format(steamId)
    )

    companion object {

        private const val NETWORK_EXCEPTION_CODE = 600
        private const val STEAM_ACCOUNT_PROXY_IS_NOT_EXISTS_EXCEPTION_CODE = 601

        private const val STEAM_ACCOUNT_PROXY_IS_NOT_EXISTS_EXCEPTION_MESSAGE = "Не удалось найти прокси для steam аккаунта, steamId %d"
    }
}
