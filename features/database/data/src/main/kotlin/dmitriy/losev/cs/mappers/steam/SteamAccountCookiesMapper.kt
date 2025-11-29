package dmitriy.losev.cs.mappers.steam

import dmitriy.losev.cs.dto.steam.SteamAccountCookiesDTO
import org.koin.core.annotation.Factory

@Factory
class SteamAccountCookiesMapper {

    fun map(value: SteamAccountCookiesDSO): SteamAccountCookiesDTO {
        return SteamAccountCookiesDTO(
            steamId = value.steamId,
            sessionId = value.sessionId,
            steamLoginSecure = value.steamLoginSecure,
            expires = value.expires
        )
    }

    fun map(value: SteamAccountCookiesDTO): SteamAccountCookiesDSO {
        return SteamAccountCookiesDSO(
            steamId = value.steamId,
            sessionId = value.sessionId,
            steamLoginSecure = value.steamLoginSecure,
            expires = value.expires
        )
    }
}