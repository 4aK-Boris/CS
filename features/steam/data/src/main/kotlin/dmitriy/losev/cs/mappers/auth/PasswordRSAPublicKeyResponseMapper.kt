package dmitriy.losev.cs.mappers.auth

import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyResponseDSO
import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyResponseDTO
import org.koin.core.annotation.Factory

@Factory
class PasswordRSAPublicKeyResponseMapper {

    fun map(value: PasswordRSAPublicKeyResponseDSO): PasswordRSAPublicKeyResponseDTO {
        return PasswordRSAPublicKeyResponseDTO(
            publickeyModulus = value.publickeyModulus,
            publickeyExponent = value.publickeyExponent,
            timeStamp = value.timeStamp.toLong()
        )
    }
}
