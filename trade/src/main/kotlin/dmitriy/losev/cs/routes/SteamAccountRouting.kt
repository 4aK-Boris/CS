package dmitriy.losev.cs.routes

import dmitriy.losev.cs.descriptions.SteamAccountDescription
import dmitriy.losev.cs.post
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

        post(
            builder = steamAccountDescription::upsertSteamAccountDescription,
            validation = steamAccountValidation.validateUpsertSteamAccount,
            processing = steamAccountService::upsertAccount
        )
    }
}
