package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusRequestDSO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusRequestDTO
import org.koin.core.annotation.Factory

@Factory
class PollAuthSessionStatusRequestMapper {

    fun map(value: PollAuthSessionStatusRequestDTO): PollAuthSessionStatusRequestDSO {
        return PollAuthSessionStatusRequestDSO(
            steamId = value.steamId,
            clientId = value.clientId,
            requestId = value.requestId,
            interval = value.interval.toString()
        )
    }
}
