package dmitriy.losev.cs.routes.steam

import io.ktor.server.routing.Routing
import io.ktor.server.routing.route

fun Routing.configureSteamRouting() {

    route("/api/steam") {

        configureSteamAccountRouting()
        configureActiveSteamAccountRouting()
    }
}
