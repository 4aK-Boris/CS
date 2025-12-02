package dmitriy.losev.cs.cookie

fun interface CookieStorageHandlerFactory {

    fun create(steamId: Long): CookieStorageHandler
}
