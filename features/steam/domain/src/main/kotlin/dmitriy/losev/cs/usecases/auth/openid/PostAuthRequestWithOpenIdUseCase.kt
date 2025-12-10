package dmitriy.losev.cs.usecases.auth.openid

import dmitriy.losev.cs.dto.auth.openid.PostRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.PostResponseOpenIdParamsDTO
import dmitriy.losev.cs.repositories.SteamAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class PostAuthRequestWithOpenIdUseCase(@Provided private val steamAuthRepository: SteamAuthRepository) : BaseUseCase {

    suspend operator fun invoke(postRequestOpenIdParams: PostRequestOpenIdParamsDTO): Result<PostResponseOpenIdParamsDTO> = runCatching {
        steamAuthRepository.postAuthRequestWithOpenId(postRequestOpenIdParams) ?: error("Location for OpenId Auth is null")
    }
}
