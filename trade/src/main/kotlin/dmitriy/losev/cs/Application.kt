package dmitriy.losev.cs

import dmitriy.losev.cs.configs.configureLogging
import dmitriy.losev.cs.configs.configureOpenApi
import dmitriy.losev.cs.configs.configureSerialization
import dmitriy.losev.cs.configs.configureStatusPages
import dmitriy.losev.cs.configs.loadConfig
import dmitriy.losev.cs.routes.configureProxyRouting
import dmitriy.losev.cs.routes.steam.configureSteamRouting
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.routing.routing
import org.koin.dsl.module
import org.koin.ksp.generated.module
import org.koin.ktor.plugin.Koin
import org.koin.ktor.plugin.KoinApplicationStarted
import org.koin.ktor.plugin.KoinApplicationStopPreparing
import org.koin.ktor.plugin.KoinApplicationStopped
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    ConsoleEncodingFix.fix()

    val appModule = module {

        single { loadConfig() }

        includes(AppModule().module)
    }

    install(plugin = Koin) {
        slf4jLogger()
        modules(modules = appModule)
    }

    monitor.subscribe(definition = KoinApplicationStarted) {
        log.info("Koin started.")
    }

    monitor.subscribe(definition = KoinApplicationStopPreparing) {
        log.info("Koin stopping...")
    }

    monitor.subscribe(definition = KoinApplicationStopped) {
        log.info("Koin stopped.")
    }

    configureOpenApi()
    configureSerialization()
    configureStatusPages()
    configureLogging()

    routing {
        configureSteamRouting()
        configureProxyRouting()
    }
}
