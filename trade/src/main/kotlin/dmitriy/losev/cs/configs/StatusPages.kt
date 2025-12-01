package dmitriy.losev.cs.configs

import dmitriy.losev.cs.api.ApiResponse
import dmitriy.losev.cs.exceptions.UnauthorizedException
import dmitriy.losev.cs.exceptions.ValidationException
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.plugins.NotFoundException
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.request.ContentTransformationException
import io.ktor.server.response.respond

fun Application.configureStatusPages() {

    install(StatusPages) {

        // Ошибки валидации
        exception<ValidationException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse.Error(message = "Validation failed", code = "VALIDATION_ERROR", details = cause.errors)
            )
        }

        // Неверный формат JSON
        exception<ContentTransformationException> { call, cause ->
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse.Error(message = "Invalid request body", code = "INVALID_JSON")
            )
        }

        // Не найдено
        exception<NotFoundException> { call, cause ->
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ApiResponse.Error(message = cause.message ?: "Resource not found", code = "NOT_FOUND")
            )
        }

        // Не авторизован
        exception<UnauthorizedException> { call, cause ->
            call.respond(
                status = HttpStatusCode.Unauthorized,
                message = ApiResponse.Error(message = cause.message ?: "Unauthorized", code = "UNAUTHORIZED")
            )
        }

        // Все остальные ошибки (500)
        exception<Throwable> { call, cause ->
            // Логируем для дебага
            call.application.log.error("Unhandled exception", cause)

            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ApiResponse.Error(message = "Internal server error", code = "INTERNAL_ERROR")
            )
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
