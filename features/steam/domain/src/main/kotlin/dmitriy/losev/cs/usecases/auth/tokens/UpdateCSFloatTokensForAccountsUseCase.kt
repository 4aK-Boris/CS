package dmitriy.losev.cs.usecases.auth.tokens

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.auth.openid.AuthWithOpenIdInOtherServicesUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.GetAccountsWithExpiringCsFloatTokenUseCase
import org.koin.core.annotation.Factory

@Factory
class UpdateCSFloatTokensForAccountsUseCase(
    private val getAccountsWithExpiringCsFloatTokenUseCase: GetAccountsWithExpiringCsFloatTokenUseCase,
    private val authWithOpenIdInOtherServicesUseCase: AuthWithOpenIdInOtherServicesUseCase
) : BaseUseCase {

    suspend operator fun invoke(): Result<List<Result<Long>>> {
        return getAccountsWithExpiringCsFloatTokenUseCase.invoke().mapCatching { steamIds ->
            steamIds.mapConcurrency { steamId ->
                authWithOpenIdInOtherServicesUseCase.invoke(steamId)
            }
        }
    }
}
