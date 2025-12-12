package dmitriy.losev.cs.core

import kotlin.concurrent.atomics.AtomicReference
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.concurrent.atomics.updateAndFetch
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class, ExperimentalAtomicApi::class)
class TokenHolder {
    private data class TokenData(
        val value: String,
        val expiresAt: Instant
    )

    private val state = AtomicReference<TokenData?>(null)

    val token: String?
        get() = state.load()?.takeIf { tokenData -> tokenData.expiresAt > currentTime }?.value

    val isExpired: Boolean
        get() = state.load()?.let { tokenData -> tokenData.expiresAt <= currentTime } ?: true

    fun update(token: String, expiresIn: Duration = 300.days): String {
        state.updateAndFetch { TokenData(token, currentTime.plus(expiresIn)) }
        return token
    }

    private val currentTime: Instant
        get() = Clock.System.now()
}
