package dmitriy.losev.cs.di

import dmitriy.losev.cs.AesCrypto
import dmitriy.losev.cs.Context
import dmitriy.losev.cs.Database
import dmitriy.losev.cs.cookie.CookieStorageHandlerFactory
import dmitriy.losev.cs.cookie.DatabaseCookieStorageHandlerFactory
import dmitriy.losev.cs.cookie.DatabaseTaskLogger
import dmitriy.losev.cs.tasks.TaskLogger
import dmitriy.losev.cs.handlers.CookieHandler
import dmitriy.losev.cs.handlers.CookieHandlerImpl
import dmitriy.losev.cs.handlers.ProxyHandler
import dmitriy.losev.cs.handlers.ProxyHandlerImpl
import dmitriy.losev.cs.handlers.system.TaskLogHandler
import dmitriy.losev.cs.handlers.system.TaskLogHandlerImpl
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Module
class DatabaseCoreModule {

    @Singleton
    fun getCookieHandler(@Provided json: Json, database: Database, aesCrypto: AesCrypto): CookieHandler {
        return CookieHandlerImpl(json, database, aesCrypto)
    }

    @Singleton
    fun getProxyHandler(database: Database, aesCrypto: AesCrypto): ProxyHandler {
        return ProxyHandlerImpl(database, aesCrypto)
    }

    @Singleton
    internal fun getCookieStorageHandlerFactory(cookieHandler: CookieHandler): CookieStorageHandlerFactory {
        return DatabaseCookieStorageHandlerFactory(cookieHandler)
    }

    @Singleton
    fun getDatabase(@Provided context: Context): Database {
        return Database(context)
    }

    @Singleton
    internal fun getTaskLogHandler(database: Database): TaskLogHandler {
        return TaskLogHandlerImpl(database)
    }

    @Singleton
    fun getTaskLogger(taskLogHandler: TaskLogHandler): TaskLogger {
        return DatabaseTaskLogger(taskLogHandler)
    }
}
