package dmitriy.losev.cs.repositories.cookie

import dmitriy.losev.cs.cookie.CookieCacheUpdater
import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO
import dmitriy.losev.cs.handlers.CookieHandler
import dmitriy.losev.cs.mappers.cookie.NetworkCookieMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [DatabaseCookieRepository::class])
class DatabaseCookieRepositoryImpl(
    @Provided private val cookieCacheUpdater: CookieCacheUpdater,
    private val cookieHandler: CookieHandler,
    private val networkCookieMapper: NetworkCookieMapper
): DatabaseCookieRepository {

    override suspend fun saveCookies(cookies: List<NetworkCookieDTO>) {
        val networkCookie = cookies.map(transform = networkCookieMapper::map)
        cookieHandler.saveCookies(cookies = networkCookie)
        cookieCacheUpdater.updateCache(cookies = networkCookie)
    }
}
