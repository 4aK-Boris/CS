package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenUseCase(
    @Provided private val context: Context,
    private val getAuthTokenFromCacheUseCase: GetAuthTokenFromCacheUseCase,
    private val setAuthTokenToCacheUseCase: SetAuthTokenToCacheUseCase,
    private val getAuthTokenFromNetworkUseCase: GetAuthTokenFromNetworkUseCase
) : BaseUseCase {

    suspend operator fun invoke(): Result<String> {
        return getAuthTokenFromCacheUseCase.invoke().recoverCatching {
            val authRequest = AuthRequestDTO(email = context.pulseConfig.email, password = context.pulseConfig.password)
            getAuthTokenFromNetworkUseCase.invoke(authRequest).mapCatching { token ->
                setAuthTokenToCacheUseCase.invoke(token = token.authData.token).getOrThrow()
            }.getOrThrow()
        }
    }
}
