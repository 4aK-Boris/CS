package dmitriy.losev.cs.configs

import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.exceptions.ExceptionHandler
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.respond

fun Application.configureStatusPages() {

    install(StatusPages) {

        // Неверный формат JSON - обрабатываем отдельно, так как это ошибка Ktor
        exception<ContentTransformationException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse.Error(message = "Invalid request body", code = "INVALID_JSON")
            )
        }

        // Все остальные исключения обрабатываются через ExceptionHandler
        exception<Throwable> { call, cause ->
            ExceptionHandler.handleHttpException(call, cause)
        }

        // Обработка HTTP статусов
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                status = status,
                message = ApiResponse.Error(message = "Endpoint not found", code = "ENDPOINT_NOT_FOUND")
            )
        }
    }
}
