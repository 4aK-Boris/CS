package dmitriy.losev.cs.api

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.response.respond

suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(data: T) {
    respond(ApiResponse.Success(data))
}

suspend fun ApplicationCall.respondError(status: HttpStatusCode, message: String, code: String? = null) {
    respond(status, ApiResponse.Error(message, code))
}
