package dmitriy.losev.cs.configs

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.DatabaseConfig
import dmitriy.losev.cs.MobileProxyDeviceConfig
import dmitriy.losev.cs.Environment
import dmitriy.losev.cs.HttpLoggingConfig
import dmitriy.losev.cs.MobileProxyConfig
import io.ktor.server.application.Application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

fun Application.loadConfig(): Context {

    val config = environment.config

    val stringEnvironment = config.property("ktor.app.environment").getString()

    val environment = when (stringEnvironment) {
        "prod" -> Environment.Production
        "dev" -> Environment.Development
        else -> Environment.Development
    }

    val debug = config.property("ktor.app.debug").getString().toBoolean()

    val aesKey = config.property(path = "ktor.crypto.aesKey").getString()

    val databaseUrl = config.property(path = "ktor.database.url").getString()
    val databaseUser = config.property(path = "ktor.database.user").getString()
    val databasePassword = config.property(path = "ktor.database.password").getString()

    val httpLoggingConfig = config.config("ktor.logging.http")

    val httpLoggingConfigEnabled = httpLoggingConfig.property("enabled").getString().toBoolean()
    val httpLoggingConfigLevel = httpLoggingConfig.property("level").getString()
    val httpLoggingConfigExcludedPaths = httpLoggingConfig.property("excludedPaths").getList()
    val httpLoggingConfigSensitiveFields = httpLoggingConfig.property("sensitiveFields").getList()
    val httpLoggingConfigSensitiveHeaders = httpLoggingConfig.property("sensitiveHeaders").getList().map(transform = String::lowercase)
    val httpLoggingConfigMaxBodySize = httpLoggingConfig.property("maxBodySize").getString().toInt()

    val mobileProxyDeviceConfigs = config.configList("ktor.proxy.devices").map { mobileProxyDeviceConfig ->
        MobileProxyDeviceConfig(
            deviceId = mobileProxyDeviceConfig.property("deviceId").getString(),
            port = mobileProxyDeviceConfig.property("port").getString().toInt(),
            deviceName = mobileProxyDeviceConfig.property("deviceName").getString(),
            login = mobileProxyDeviceConfig.property("login").getString(),
            password = mobileProxyDeviceConfig.property("password").getString()
        )
    }

    return Context(
        coroutineContext = SupervisorJob() + Dispatchers.IO,
        environment = environment,
        debug = debug,
        aesKey = aesKey,
        databaseConfig = DatabaseConfig(
            databaseUrl = databaseUrl,
            databaseUser = databaseUser,
            databasePassword = databasePassword
        ),
        mobileProxyConfig = MobileProxyConfig(mobileProxyDeviceConfigs),
        httpLoggingConfig = HttpLoggingConfig(
            enabled = httpLoggingConfigEnabled,
            level = httpLoggingConfigLevel,
            excludedPaths = httpLoggingConfigExcludedPaths,
            sensitiveFields = httpLoggingConfigSensitiveFields,
            sensitiveHeaders = httpLoggingConfigSensitiveHeaders,
            maxBodySize = httpLoggingConfigMaxBodySize
        )
    )
}
