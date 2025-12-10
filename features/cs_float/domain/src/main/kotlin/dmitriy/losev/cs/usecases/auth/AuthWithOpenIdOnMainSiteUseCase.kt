package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.OpenIdParamsDTO
import dmitriy.losev.cs.repositories.CsFloatAuthRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class AuthWithOpenIdOnMainSiteUseCase(@Provided private val csFloatAuthRepository: CsFloatAuthRepository) : BaseUseCase {

    suspend operator fun invoke(openIdParams: OpenIdParamsDTO): Result<String> = runCatching {
        csFloatAuthRepository.authWithOpenIdOnMainSite(openIdParams)
    }
}
