package dmitriy.losev.cs.configs

import dmitriy.losev.cs.core.json
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.AuthScheme
import io.github.smiley4.ktoropenapi.config.AuthType
import io.github.smiley4.ktoropenapi.config.ExampleEncoder
import io.github.smiley4.ktoropenapi.config.SchemaGenerator
import io.github.smiley4.ktoropenapi.openApi
import io.github.smiley4.ktorswaggerui.swaggerUI
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.routing.route
import io.ktor.server.routing.routing

fun Application.configureOpenApi() {

    install(OpenApi) {

        info {
            title = "CS2 Trade API"
            version = "0.0.1"
        }

        security {

            securityScheme("BasicAuth") {
                type = AuthType.HTTP
                scheme = AuthScheme.BASIC
            }
        }

        schemas {
            generator = SchemaGenerator.kotlinx(json)
        }

        examples {
            exampleEncoder = ExampleEncoder.kotlinx(json)
        }
    }

    routing {
        route(path = "/api.json") {
            openApi()
        }

        route(path = "/swagger") {
            swaggerUI(openApiUrl = "/api.json")
        }
    }
}
