package dmitriy.losev.cs.exceptions

//2000-2999
sealed class DatabaseException(errorCode: Int, message: String?) : BaseException(errorCode, message) {

    class UnsuccessfulInsertItemInfoException(itemName: String) : DatabaseException(
        errorCode = UNSUCCESSFUL_INSERT_ITEM_INFO_EXCEPTION_CODE,
        message = UNSUCCESSFUL_INSERT_ITEM_INFO_EXCEPTION_MESSAGE.format(itemName)
    )

    class UnsuccessfulInsertCharmInfoException(name: String) : DatabaseException(
        errorCode = UNSUCCESSFUL_INSERT_CHARM_INFO_EXCEPTION_CODE,
        message = UNSUCCESSFUL_INSERT_CHARM_INFO_EXCEPTION_MESSAGE.format(name)
    )

    class SteamAccountNotFoundException(steamId: ULong) : DatabaseException(
        errorCode = STEAM_ACCOUNT_NOT_FOUND_EXCEPTION_CODE,
        message = STEAM_ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE.format(steamId)
    )

    companion object {
        private const val UNSUCCESSFUL_INSERT_ITEM_INFO_EXCEPTION_CODE = 2000
        private const val UNSUCCESSFUL_INSERT_CHARM_INFO_EXCEPTION_CODE = 2001
        private const val STEAM_ACCOUNT_NOT_FOUND_EXCEPTION_CODE = 2002

        private const val UNSUCCESSFUL_INSERT_ITEM_INFO_EXCEPTION_MESSAGE = "Не удалось вставить предмет с названием %s"
        private const val UNSUCCESSFUL_INSERT_CHARM_INFO_EXCEPTION_MESSAGE = "Не удалось вставить брелок с названием %s"
        private const val STEAM_ACCOUNT_NOT_FOUND_EXCEPTION_MESSAGE = "Steam аккаунт с ID %d не найден"
    }
}