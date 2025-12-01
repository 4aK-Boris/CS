package dmitriy.losev.cs.usecases

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

interface BaseUseCase {

    fun <T: Any> T?.requireNotNull(lazyMessage: () -> Any): T {
        return requireNotNull(value = this, lazyMessage = lazyMessage)
    }

    suspend fun <T : Any, R : Any> createResult(data: List<T>, results: List<Result<R>>) {

    }

    suspend fun <T, R> List<T>.mapConcurrency(f: suspend (T) -> R): List<R> = coroutineScope {
        map { async { f(it) } }.awaitAll()
    }

    suspend fun <T, R> List<List<T>>.flatMapConcurrency(f: suspend (List<T>) -> List<R>): List<R> = coroutineScope {
        mapConcurrency(f).flatten()
    }

    suspend fun <T, R1, R2> List<List<T>>.flatMapConcurrencyToMap(f: suspend (List<T>) -> List<Pair<R1, R2>>): Map<R1, R2> = coroutineScope {
        flatMapConcurrency(f).toMap()
    }

    suspend fun <T, R> List<Result<T>>.mapFromResultToResult(f: suspend (T) -> Result<R>): List<Result<R>> {
        return map { runCatching { f(it.getOrThrow()).getOrThrow() } }
    }

    suspend fun <T, R> List<Result<T>>.mapConcurrencyFromResultToResult(f: suspend (T) -> Result<R>): List<Result<R>> = coroutineScope {
        map { async { runCatching { f(it.getOrThrow()).getOrThrow() } } }.awaitAll()
    }

    suspend fun <T, R> List<Result<T>>.mapFromResult(f: suspend (T) -> R): List<Result<R>> {
        return map { runCatching { f(it.getOrThrow()) } }
    }

    suspend fun <T, R> List<Result<T>>.mapConcurrencyFromResult(f: suspend (T) -> R): List<Result<R>> = coroutineScope {
        map { async { runCatching { f(it.getOrThrow()) } } }.awaitAll()
    }

    suspend fun launchFun(
        f1: suspend () -> Unit,
        f2: suspend () -> Unit,
        f3: suspend () -> Unit = { },
        f4: suspend () -> Unit = { },
        f5: suspend () -> Unit = { }
    ): Unit = coroutineScope {
        launch { f1() }
        launch { f2() }
        launch { f3() }
        launch { f4() }
        launch { f5() }
    }
}
