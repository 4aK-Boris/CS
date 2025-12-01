package dmitriy.losev.cs.exceptions

//3000-3999
sealed class SteamException(errorCode: Int, message: String?) : BaseException(errorCode, message) {

    class NullableSteamLoginSecureCookieException(steamId: ULong) : SteamException(
        errorCode = NULLABLE_STEAM_LOGIN_SECURE_COOKIE_EXCEPTION_CODE,
        message = NULLABLE_STEAM_LOGIN_SECURE_COOKIE_EXCEPTION_MESSAGE.format(steamId)
    )

    class NullableSessionIdCookieException(steamId: ULong) : SteamException(
        errorCode = NULLABLE_SESSION_ID_COOKIE_EXCEPTION_CODE,
        message = NULLABLE_SESSION_ID_COOKIE_EXCEPTION_MESSAGE.format(steamId)
    )

    class NullableSteamAccountCookiesException(steamId: ULong) : SteamException(
        errorCode = NULLABLE_STEAM_ACCOUNT_COOKIES_EXCEPTION_CODE,
        message = NULLABLE_STEAM_ACCOUNT_COOKIES_EXCEPTION_MESSAGE.format(steamId)
    )

    class SteamAccountCookiesIsRottenException(steamId: ULong) : SteamException(
        errorCode = STEAM_ACCOUNT_COOKIES_IS_ROTTEN_EXCEPTION_CODE,
        message = STEAM_ACCOUNT_COOKIES_IS_ROTTEN_EXCEPTION_MESSAGE.format(steamId)
    )

    companion object {
        private const val NULLABLE_STEAM_LOGIN_SECURE_COOKIE_EXCEPTION_CODE = 3000
        private const val NULLABLE_SESSION_ID_COOKIE_EXCEPTION_CODE = 3001
        private const val NULLABLE_STEAM_ACCOUNT_COOKIES_EXCEPTION_CODE = 3002
        private const val STEAM_ACCOUNT_COOKIES_IS_ROTTEN_EXCEPTION_CODE = 3003

        private const val NULLABLE_STEAM_LOGIN_SECURE_COOKIE_EXCEPTION_MESSAGE = "Куки SteamLoginSecure для аккаунта %d имеет значение null"
        private const val NULLABLE_SESSION_ID_COOKIE_EXCEPTION_MESSAGE = "Куки SessionId для аккаунта %d имеет значение null"
        private const val NULLABLE_STEAM_ACCOUNT_COOKIES_EXCEPTION_MESSAGE = "Куки для аккаунта %d имеет значение null"
        private const val STEAM_ACCOUNT_COOKIES_IS_ROTTEN_EXCEPTION_MESSAGE = "Куки для аккаунта %d протухли"
    }
}
