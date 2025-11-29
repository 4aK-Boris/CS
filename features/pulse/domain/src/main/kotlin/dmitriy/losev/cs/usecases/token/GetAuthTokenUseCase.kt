package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.Context
import dmitriy.losev.cs.dto.auth.AuthRequestDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import java.time.LocalDateTime
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenUseCase(
    @Provided private val context: Context,
    private val getAuthTokenFromDataStoreUseCase: GetAuthTokenFromDataStoreUseCase,
    private val setAuthTokenFromDataStoreUseCase: SetAuthTokenFromDataStoreUseCase,
    private val getAuthTokenFromNetworkUseCase: GetAuthTokenFromNetworkUseCase,
    private val getAuthTokenLastUpdateUseCase: GetAuthTokenLastUpdateUseCase,
    private val setAuthTokenLastUpdateUseCase: SetAuthTokenLastUpdateUseCase
) : BaseUseCase {

    suspend operator fun invoke(): Result<String> {
        return getAuthTokenLastUpdateUseCase.invoke()
            .mapCatchingInData { authTokenLastUpdate ->
                if (authTokenLastUpdate.isBefore(dateTime.minusHours(8))) {
                    throw Exception()
                } else {
                    getAuthTokenFromDataStoreUseCase.invoke()
                }
            }.recoverCatching {
                updateToken().getOrThrow()
            }
    }

    private suspend fun updateToken(): Result<String> {
        val authRequest = AuthRequestDTO(email = context.pulseConfig.email, password = context.pulseConfig.password)
        return getAuthTokenFromNetworkUseCase.invoke(authRequest).mapCatching { authToken ->
            setAuthTokenFromDataStoreUseCase.invoke(authToken = authToken.authData.token).getOrThrow().apply {
                setAuthTokenLastUpdateUseCase.invoke().getOrThrow()
            }
        }
    }

    private val dateTime: LocalDateTime
        get() = LocalDateTime.now()
}