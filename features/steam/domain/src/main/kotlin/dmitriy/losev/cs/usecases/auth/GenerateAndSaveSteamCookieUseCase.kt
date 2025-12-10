package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.cookie.SaveCookiesUseCase
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.koin.core.annotation.Factory

@Factory
class GenerateAndSaveSteamCookieUseCase(private val saveCookiesUseCase: SaveCookiesUseCase) : BaseUseCase {

    suspend operator fun invoke(steamId: Long, accessToken: String, sessionId: String): Result<Unit> {
        val cookies = steamDomains.flatMap { domain ->
            listOf(
                getSteamLoginSecureCookie(steamId, domain, accessToken),
                getSessionIdCookie(steamId, domain, sessionId),
                getMobileClientCookie(steamId, domain),
                getMobileClientVersionCookie(steamId, domain)
            )
        }
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

    private fun getSessionIdCookie(steamId: Long, domain: String, sessionId: String): NetworkCookieDTO {
        return NetworkCookieDTO(
            steamId = steamId,
            name = SESSION_ID_COOKIE_NAME,
            value = sessionId,
            maxAge = FIVE_YEARS,
            expires = fiveYearsDateTime,
            domain = domain,
            path = PATH
        )
    }

    private fun getMobileClientCookie(steamId: Long, domain: String): NetworkCookieDTO {
        return NetworkCookieDTO(
            steamId = steamId,
            name = MOBILE_CLIENT_COOKIE_NAME,
            value = "android",
            maxAge = FIVE_YEARS,
            expires = fiveYearsDateTime,
            domain = domain,
            path = PATH
        )
    }

    private fun getMobileClientVersionCookie(steamId: Long, domain: String): NetworkCookieDTO {
        return NetworkCookieDTO(
            steamId = steamId,
            name = MOBILE_CLIENT_VERSION_COOKIE_NAME,
            value = "777777 3.7.2",
            maxAge = FIVE_YEARS,
            expires = fiveYearsDateTime,
            domain = domain,
            path = PATH
        )
    }

    private val fiveYearsDateTime: Instant
        get() = currentDateTime.plus(5 * 365, ChronoUnit.DAYS)

    private val nextDay: Instant
        get() = currentDateTime.plus(1, ChronoUnit.DAYS)

    private val currentDateTime: Instant
        get() = Instant.now()

    companion object {

        private const val STEAM_LOGIN_SECURE_COOKIE_NAME = "steamLoginSecure"
        private const val SESSION_ID_COOKIE_NAME = "sessionid"
        private const val MOBILE_CLIENT_COOKIE_NAME = "mobileClient"
        private const val MOBILE_CLIENT_VERSION_COOKIE_NAME = "mobileClientVersion"

        private const val PATH = "/"

        private const val ONE_DAY = 86400
        private const val FIVE_YEARS = 157680000

        private val steamDomains = listOf(
            "store.steampowered.com",
            "steamcommunity.com",
            "help.steampowered.com",
            "checkout.steampowered.com",
            "steam.tv"
        )
    }
}
