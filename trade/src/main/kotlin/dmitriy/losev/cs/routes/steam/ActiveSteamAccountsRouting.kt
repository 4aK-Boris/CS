package dmitriy.losev.cs.routes.steam

import dmitriy.losev.cs.descriptions.ActiveSteamAccountDescription
import dmitriy.losev.cs.extractors.ActiveSteamAccountExtractor
import dmitriy.losev.cs.getHandle
import dmitriy.losev.cs.postHandle
import dmitriy.losev.cs.services.ActiveSteamAccountService
import dmitriy.losev.cs.validations.ActiveSteamAccountValidation
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.configureActiveSteamAccountRouting() {

    val activeSteamAccountService by inject<ActiveSteamAccountService>()

    val activeSteamAccountDescription by inject<ActiveSteamAccountDescription>()

    val activeSteamAccountValidation by inject<ActiveSteamAccountValidation>()

    val activeSteamAccountExtractor by inject<ActiveSteamAccountExtractor>()


    route(path = "/account/active") {

        postHandle(
            builder = activeSteamAccountDescription::upsertActiveSteamAccountDescription,
            validation = activeSteamAccountValidation.validateUpsertActiveSteamAccount,
            processing = activeSteamAccountService::upsertActiveSteamAccount
        )

        route(path = "/by_steam_id") {

            getHandle(
                builder = activeSteamAccountDescription::getActiveSteamAccountBySteamIdDescription,
                validation = activeSteamAccountValidation.validateGetActiveSteamAccountBySteamId,
                extractor = activeSteamAccountExtractor::extractSteamId,
                processing = activeSteamAccountService::getActiveSteamAccountBySteamId
            )
        }

        route(path = "/by_login") {

            getHandle(
                builder = activeSteamAccountDescription::getActiveSteamAccountByLoginDescription,
                validation = activeSteamAccountValidation.validateGetActiveSteamAccountByLogin,
                extractor = activeSteamAccountExtractor::extractLogin,
                processing = activeSteamAccountService::getActiveSteamAccountByLogin
            )
        }
    }


    route(path = "/accounts/active") {

        getHandle(
            builder = activeSteamAccountDescription::getAllActiveSteamAccountDescription,
            processing = activeSteamAccountService::getAllActiveSteamAccounts
        )
    }
}
