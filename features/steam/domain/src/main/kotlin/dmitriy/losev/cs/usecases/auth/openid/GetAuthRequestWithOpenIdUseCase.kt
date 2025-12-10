package dmitriy.losev.cs.usecases.auth.openid

import dmitriy.losev.cs.dto.auth.openid.GetRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.GetResponseOpenIdParamsDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthRequestWithOpenIdUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(getRequestOpenIdParams: GetRequestOpenIdParamsDTO): Result<GetResponseOpenIdParamsDTO> = runCatching {
        steamAuthRepository.getAuthRequestWithOpenId(getRequestOpenIdParams)
    }
}
