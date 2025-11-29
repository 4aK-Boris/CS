package dmitriy.losev.cs.exceptions

//1-1000
sealed class BaseException(override val errorCode: Int, override val message: String? = null): CSPanelException(errorCode, message) {

    class BaseProcessIdNotFoundException(steamProcessId: Int): BaseException(
        errorCode = CS_PROCESS_ID_NOT_FOUND_EXCEPTION_ERROR_CODE,
        message = CS_PROCESS_ID_NOT_FOUND_EXCEPTION_MESSAGE.format(steamProcessId)
    )

    companion object {

        private const val CS_PROCESS_ID_NOT_FOUND_EXCEPTION_ERROR_CODE = 1

        private const val CS_PROCESS_ID_NOT_FOUND_EXCEPTION_MESSAGE = "Не удалось найти id процесса CS2 по id %d процесса Steam"
    }
}