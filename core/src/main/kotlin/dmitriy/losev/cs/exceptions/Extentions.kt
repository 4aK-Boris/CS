package dmitriy.losev.cs.exceptions

import kotlin.reflect.KFunction

fun <T: Any, R: BaseException> requiredNotNullException(value: T?, constructor: KFunction<BaseException>, lazyMessage: () -> Any): T {
    val message = lazyMessage().toString()
    return value ?: throw constructor.call(message)
}
