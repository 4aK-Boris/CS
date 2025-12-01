package dmitriy.losev.cs.configs

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.core.createHttpLoggingPlugin
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.ext.inject

fun Application.configureLogging() {

    val context by inject<Context>()

    val plugin = createHttpLoggingPlugin(config = context.httpLoggingConfig)

    install(plugin)
}
