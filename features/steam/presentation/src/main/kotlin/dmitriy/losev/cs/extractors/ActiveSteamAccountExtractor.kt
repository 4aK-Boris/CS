package dmitriy.losev.cs.extractors

import dmitriy.losev.cs.EMPTY_STRING
import dmitriy.losev.cs.core.LOGIN_PARAMETER_NAME
import dmitriy.losev.cs.core.STEAM_ID_PARAMETER_NAME
import io.ktor.server.application.ApplicationCall
import org.koin.core.annotation.Singleton

@Singleton
class ActiveSteamAccountExtractor {

    fun extractSteamId(call: ApplicationCall): String {
        return call.parameters[STEAM_ID_PARAMETER_NAME] ?: EMPTY_STRING
    }

    fun extractLogin(call: ApplicationCall): String {
        return call.parameters[LOGIN_PARAMETER_NAME] ?: EMPTY_STRING
    }
}
