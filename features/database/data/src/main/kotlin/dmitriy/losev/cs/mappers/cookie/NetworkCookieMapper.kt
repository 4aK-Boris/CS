package dmitriy.losev.cs.mappers.cookie

import dmitriy.losev.cs.cookie.NetworkCookie
import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO
import java.time.Instant
import org.koin.core.annotation.Factory

@Factory
class NetworkCookieMapper {

    fun map(value: NetworkCookie): NetworkCookieDTO {
        return NetworkCookieDTO(
            steamId = value.steamId,
            name = value.name,
            value = value.value,
            encoding = value.encoding,
            maxAge = value.maxAge,
            expires = value.expires?.let { expires -> Instant.ofEpochMilli(expires) },
            domain = value.domain,
            path = value.path,
            secure = value.secure,
            httpOnly = value.httpOnly,
            extensions = value.extensions
        )
    }

    fun map(value: NetworkCookieDTO): NetworkCookie {
        return NetworkCookie(
            steamId = value.steamId,
            name = value.name,
            value = value.value,
            encoding = value.encoding,
            maxAge = value.maxAge,
            expires = value.expires?.toEpochMilli(),
            domain = value.domain,
            path = value.path,
            secure = value.secure,
            httpOnly = value.httpOnly,
            extensions = value.extensions
        )
    }
}
