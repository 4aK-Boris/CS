package dmitriy.losev.cs.routes.steam

import dmitriy.losev.cs.descriptions.SteamAccountDescription
import dmitriy.losev.cs.extractors.SteamAccountExtractor
import dmitriy.losev.cs.getHandle
import dmitriy.losev.cs.postHandle
import dmitriy.losev.cs.services.SteamAccountService
import dmitriy.losev.cs.validations.SteamAccountValidation
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.configureSteamAccountRouting() {

    val steamAccountService by inject<SteamAccountService>()

    val steamAccountDescription by inject<SteamAccountDescription>()

    val steamAccountValidation by inject<SteamAccountValidation>()

    val steamAccountExtractor by inject<SteamAccountExtractor>()

    route(path = "/account") {

        postHandle(
            builder = steamAccountDescription::upsertSteamAccountDescription,
            validation = steamAccountValidation.validateUpsertSteamAccount,
            processing = steamAccountService::upsertSteamAccount
        )

        route(path = "/by_steam_id") {

            getHandle(
                builder = steamAccountDescription::getSteamAccountBySteamIdDescription,
                validation = steamAccountValidation.validateGetSteamAccountBySteamId,
                extractor = steamAccountExtractor::extractSteamId,
                processing = steamAccountService::getSteamAccountBySteamId
            )
        }

        route(path = "/by_login") {

            getHandle(
                builder = steamAccountDescription::getSteamAccountByLoginDescription,
                validation = steamAccountValidation.validateGetSteamAccountByLogin,
                extractor = steamAccountExtractor::extractLogin,
                processing = steamAccountService::getSteamAccountByLogin
            )
        }
    }


    route(path = "/accounts") {

        getHandle(
            builder = steamAccountDescription::getAllSteamAccountDescription,
            processing = steamAccountService::getAllSteamAccounts
        )
    }
}
