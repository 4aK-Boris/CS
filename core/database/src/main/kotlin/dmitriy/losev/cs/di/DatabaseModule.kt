package dmitriy.losev.cs.di

import dmitriy.losev.cs.Database
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.cookie.DatabaseCookieStorageHandlerFactory
import dmitriy.losev.cs.handlers.CookieHandler
import dmitriy.losev.cs.handlers.CookieHandlerImpl
import org.koin.core.annotation.Module
import org.koin.core.annotation.Singleton

@Module
class DatabaseModule {

    @Singleton
    internal fun getCookieHandler(database: Database): CookieHandler {
        return CookieHandlerImpl(database = database)
    }

    @Singleton
    internal fun getCookieStorageHandlerFactory(cookieHandler: CookieHandler): CookieStorageHandlerFactory {
        return DatabaseCookieStorageHandlerFactory(cookieHandler)
    }
}
