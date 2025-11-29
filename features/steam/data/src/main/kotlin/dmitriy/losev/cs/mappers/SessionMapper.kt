package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.SessionDSO
import dmitriy.losev.cs.dto.SessionDTO
import org.koin.core.annotation.Factory

@Factory
internal class SessionMapper {

    fun map(value: SessionDSO): SessionDTO {
        return SessionDTO(
            sessionId = value.sessionId,
            steamId = value.steamId,
            accessToken = value.accessToken,
            refreshToken = value.refreshToken
        )
    }

    fun map(value: SessionDTO): SessionDSO {
        return SessionDSO(
            sessionId = value.sessionId,
            steamId = value.steamId,
            accessToken = value.accessToken,
            refreshToken = value.refreshToken
        )
    }
}