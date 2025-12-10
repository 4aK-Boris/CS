package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusResponseDSO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusResponseDTO
import org.koin.core.annotation.Factory

@Factory
class PollAuthSessionStatusResponseMapper {

    fun map(value: PollAuthSessionStatusResponseDSO): PollAuthSessionStatusResponseDTO {
        return PollAuthSessionStatusResponseDTO(
            refreshToken = value.refreshToken,
            accessToken = value.accessToken,
            hadRemoteInteraction = value.hadRemoteInteraction,
            login = value.login
        )
    }
}
