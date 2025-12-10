package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.cookie.SaveCookiesUseCase
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.koin.core.annotation.Factory

@Factory
class GenerateAndSaveSteamLoginSecureCookieUseCase(private val saveCookiesUseCase: SaveCookiesUseCase) : BaseUseCase {

    suspend operator fun invoke(steamId: Long, accessToken: String): Result<Unit> {
        val cookies = steamDomains.map { domain -> getSteamLoginSecureCookie(steamId, domain, accessToken) }
        return saveCookiesUseCase.invoke(cookies)
    }

    private fun getSteamLoginSecureCookie(steamId: Long, domain: String, accessToken: String): NetworkCookieDTO {
        return NetworkCookieDTO(
            steamId = steamId,
            name = STEAM_LOGIN_SECURE_COOKIE_NAME,
            value = "$steamId||$accessToken",
            maxAge = ONE_DAY,
            expires = nextDay,
            domain = domain,
            path = PATH
        )
    }

    private val nextDay: Instant
        get() = currentDateTime.plus(1, ChronoUnit.DAYS)

    private val currentDateTime: Instant
        get() = Instant.now()

    companion object {

        private const val STEAM_LOGIN_SECURE_COOKIE_NAME = "steamLoginSecure"

        private const val PATH = "/"

        private const val ONE_DAY = 86400

        private val steamDomains = listOf(
            "store.steampowered.com",
            "steamcommunity.com",
            "help.steampowered.com",
            "checkout.steampowered.com",
            "steam.tv"
        )
    }
}
