@file:Suppress("NOTHING_TO_INLINE")

package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.exceptions.CSPanelException

fun <T> Result<T>.throwException() {
    if (isFailure) {
        exceptionOrNull()?.let {
            throw it
        }
    }
}

inline fun <T> Result<T>.mapWorking(working: (value: T) -> Unit): Result<T> {
    return when {
        isSuccess -> runCatching {
            val value = getOrThrow()
            working(value)
            value
        }

        else -> this
    }
}

inline fun <R, T : R> Result<T>.recoverCatchingWithException(ozonException: CSPanelException, transform: (exception: Throwable) -> R): Result<R> {
    return when (val exception = exceptionOrNull()) {
        null -> this
        else -> runCatching {
            if (exception::class == ozonException::class) {
                transform(exception)
            } else {
                throw exception
            }
        }
    }
}

inline fun <T, R> Result<T>.mapCatchingData(working: (value: T) -> R): R {
    return mapCatching(transform = working).getOrThrow()
}

inline fun <T, R> Result<T>.mapCatchingInData(working: (value: T) -> Result<R>): Result<R> {
    return mapCatching { value -> working(value).getOrThrow() }
}

inline fun <T> Result<T>.mapWorkingData(working: (value: T) -> Unit): T {
    return when {
        isSuccess -> runCatching {
            val value = getOrThrow()
            working(value)
            value
        }

        else -> this
    }.getOrThrow()
}

fun <T> T.toResult(): Result<T> = Result.success(this)

val Result<*>.message: String?
    get() = this.exceptionOrNull()?.message


inline fun <K, V, R> Map<out K, V>.mapCatching(transform: (K, V) -> R): List<Result<R>> {
    return mapTo(destination = ArrayList<Result<R>>(size)) { (key, value) ->
        runCatching { transform(key, value) }
    }
}
