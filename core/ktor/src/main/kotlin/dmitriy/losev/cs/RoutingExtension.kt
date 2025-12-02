package dmitriy.losev.cs

import dmitriy.losev.cs.api.respondSuccess
import dmitriy.losev.cs.exceptions.ExceptionHandler
import dmitriy.losev.cs.exceptions.ValidationException
import dmitriy.losev.cs.validation.receiveValidated
import io.github.smiley4.ktoropenapi.config.RouteConfig
import io.github.smiley4.ktoropenapi.delete
import io.github.smiley4.ktoropenapi.get
import io.github.smiley4.ktoropenapi.post
import io.konform.validation.Validation
import io.ktor.server.application.ApplicationCall
import io.ktor.server.routing.Route

inline fun <reified T: Any, reified R: Any> Route.postHandle(
    noinline builder: RouteConfig.() -> Unit,
    validation: Validation<T>,
    crossinline processing: suspend (T) -> Result<R>
) {
    post(builder = builder) {

        val request = call.receiveValidated(validation)

        processing(request)
            .onSuccess { data -> call.respondSuccess(data) }
            .onFailure { exception -> ExceptionHandler.handleHttpException(call, exception) }
    }
}

inline fun <reified T: Any, reified R: Any> Route.deleteHandle(
    noinline builder: RouteConfig.() -> Unit,
    validation: Validation<T>,
    crossinline processing: suspend (T) -> Result<R>
) {
    delete(builder = builder) {

        val request = call.receiveValidated(validation)

        processing(request)
            .onSuccess { data -> call.respondSuccess(data) }
            .onFailure { exception -> ExceptionHandler.handleHttpException(call, exception) }
    }
}

inline fun <reified R : Any> Route.getHandle(
    noinline builder: RouteConfig.() -> Unit,
    crossinline processing: suspend () -> Result<R>
) {
    get(builder = builder) {

        processing()
            .onSuccess { data -> call.respondSuccess(data) }
            .onFailure { exception -> ExceptionHandler.handleHttpException(call, exception) }
    }
}

inline fun <reified T : Any, reified R : Any> Route.getHandle(
    noinline builder: RouteConfig.() -> Unit,
    validation: Validation<T>,
    noinline extractor: (ApplicationCall) -> T,
    crossinline processing: suspend (T) -> Result<R>
) {
    get(builder = builder) {
        val request = extractor(call)

        validation.validate(request).let { result ->
            if (result.errors.isNotEmpty()) {
                val messages = result.errors.map { error -> "${error.path}: ${error.message}" }
                throw ValidationException(messages)
            }
        }

        processing(request)
            .onSuccess { data -> call.respondSuccess(data) }
            .onFailure { exception -> ExceptionHandler.handleHttpException(call, exception) }
    }
}
