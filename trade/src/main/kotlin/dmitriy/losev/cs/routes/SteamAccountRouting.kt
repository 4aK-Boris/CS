package dmitriy.losev.cs.routes

import dmitriy.losev.cs.descriptions.SteamAccountDescription
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

    route("/api/steam/accounts/upsert") {

        postHandle(
            builder = steamAccountDescription::upsertSteamAccountDescription,
            validation = steamAccountValidation.validateUpsertSteamAccount,
            processing = steamAccountService::upsertAccount
        )
    }

    route("/api/steam/accounts/active/upsert") {

        postHandle(
            builder = steamAccountDescription::upsertActiveSteamAccountDescription,
            validation = steamAccountValidation.validateUpsertActiveSteamAccount,
            processing = steamAccountService::upsertActiveAccount
        )
    }
}
