package dmitriy.losev.cs.exceptions

//4000-4999
sealed class DataStoreException(errorCode: Int, message: String?) : BaseException(errorCode, message) {

    class NullablePulseAuthTokenException : DatabaseException(
        errorCode = NULLABLE_PULSE_AUTH_TOKEN_EXCEPTION_CODE,
        message = NULLABLE_PULSE_AUTH_TOKEN_EXCEPTION_MESSAGE
    )

    companion object {

        private const val NULLABLE_PULSE_AUTH_TOKEN_EXCEPTION_CODE = 4000

        private const val NULLABLE_PULSE_AUTH_TOKEN_EXCEPTION_MESSAGE = "Не удалось получить токен авторизации для Pulse из DataStore"
    }
}