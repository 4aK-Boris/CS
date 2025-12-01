package dmitriy.losev.cs.validation

import dmitriy.losev.cs.exceptions.ValidationException
import io.konform.validation.Validation
import io.ktor.server.application.ApplicationCall
import io.ktor.server.request.receive


fun <T> Validation<T>.validateOrThrow(value: T): T {
    val result = validate(value)
    if (result.isValid.not()) {
        val errors = result.errors.map { "${it.dataPath}: ${it.message}" }
        throw ValidationException(errors)
    }
    return value
}

suspend inline fun <reified T : Any> ApplicationCall.receiveValidated(validation: Validation<T>): T {
    val body = receive<T>()
    return validation.validateOrThrow(body)
}
