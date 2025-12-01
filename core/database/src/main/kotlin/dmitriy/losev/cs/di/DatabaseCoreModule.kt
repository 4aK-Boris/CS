package dmitriy.losev.cs.di

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.Database
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.cookie.DatabaseCookieStorageHandlerFactory
import dmitriy.losev.cs.handlers.CookieHandler
import dmitriy.losev.cs.handlers.CookieHandlerImpl
import dmitriy.losev.cs.handlers.ProxyHandler
import dmitriy.losev.cs.handlers.ProxyHandlerImpl
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module
class DatabaseCoreModule {

    @Singleton
    fun getCookieHandler(@Provided json: Json, database: Database): CookieHandler {
        return CookieHandlerImpl(json, database)
    }

    @Singleton
    fun getProxyHandler(database: Database): ProxyHandler {
        return ProxyHandlerImpl(database)
    }

    @Singleton
    internal fun getCookieStorageHandlerFactory(cookieHandler: CookieHandler): CookieStorageHandlerFactory {
        return DatabaseCookieStorageHandlerFactory(cookieHandler)
    }

    @Singleton
    fun getDatabase(@Provided context: Context): Database {
        return Database(context)
    }
}
