package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppResponseDSO
import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppResponseDTO
import org.koin.core.annotation.Factory

@Factory
class GenerateAccessTokenForAppResponseMapper {

    fun map(value: GenerateAccessTokenForAppResponseDSO): GenerateAccessTokenForAppResponseDTO {
        return GenerateAccessTokenForAppResponseDTO(accessToken = value.accessToken)
    }
}
