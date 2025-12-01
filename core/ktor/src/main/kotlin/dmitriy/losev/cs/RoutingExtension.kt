package dmitriy.losev.cs

import dmitriy.losev.cs.api.respondError
import dmitriy.losev.cs.api.respondSuccess
import dmitriy.losev.cs.validation.receiveValidated
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.github.smiley4.ktoropenapi.post
import io.konform.validation.Validation
import io.ktor.http.HttpStatusCode
import io.ktor.server.routing.Route

inline fun <reified T: Any, reified R: Any> Route.post(
    noinline builder: RouteConfig.() -> Unit,
    validation: Validation<T>,
    crossinline processing: suspend (T) -> Result<R>
) {
    post(builder = builder) {

        val request = call.receiveValidated(validation)

        processing(request)
            .onSuccess { data -> call.respondSuccess(data) }
            .onFailure { exception -> call.respondError(HttpStatusCode.InternalServerError, exception.message ?: "Unknown error") }
    }
}
