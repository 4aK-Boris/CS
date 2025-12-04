package dmitriy.losev.cs.routes.steam

import dmitriy.losev.cs.deleteHandle
import dmitriy.losev.cs.descriptions.ActiveSteamAccountDescription
import dmitriy.losev.cs.extractors.ActiveSteamAccountExtractor
import dmitriy.losev.cs.getHandle
import dmitriy.losev.cs.postHandle
import dmitriy.losev.cs.services.ActiveSteamAccountService
import dmitriy.losev.cs.validations.ActiveSteamAccountValidation
import dmitriy.losev.cs.validations.SteamAccountValidation
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.configureActiveSteamAccountRouting() {

    val activeSteamAccountService by inject<ActiveSteamAccountService>()

    val activeSteamAccountDescription by inject<ActiveSteamAccountDescription>()

    val steamAccountValidation by inject<SteamAccountValidation>()
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
                validation = steamAccountValidation.validateSteamId,
                extractor = activeSteamAccountExtractor::extractSteamId,
                processing = activeSteamAccountService::getActiveSteamAccountBySteamId
            )
        }

        route(path = "/by_login") {

            getHandle(
                builder = activeSteamAccountDescription::getActiveSteamAccountByLoginDescription,
                validation = steamAccountValidation.validateLogin,
                extractor = activeSteamAccountExtractor::extractLogin,
                processing = activeSteamAccountService::getActiveSteamAccountByLogin
            )
        }

        route(path = "/delete") {

            route(path = "/by_steam_id") {

                deleteHandle(
                    builder = activeSteamAccountDescription::deleteActiveSteamAccountBySteamIdDescription,
                    validation = steamAccountValidation.validateSteamId,
                    extractor = activeSteamAccountExtractor::extractSteamId,
                    processing = activeSteamAccountService::deleteSteamAccountBySteamId
                )
            }

            route(path = "/by_login") {

                deleteHandle(
                    builder = activeSteamAccountDescription::deleteActiveSteamAccountByLoginDescription,
                    validation = steamAccountValidation.validateLogin,
                    extractor = activeSteamAccountExtractor::extractLogin,
                    processing = activeSteamAccountService::deleteSteamAccountByLogin
                )
            }
        }
    }


    route(path = "/accounts/active") {

        getHandle(
            builder = activeSteamAccountDescription::getAllActiveSteamAccountDescription,
            processing = activeSteamAccountService::getAllActiveSteamAccounts
        )
    }
}
