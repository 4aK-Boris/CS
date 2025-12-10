package dmitriy.losev.cs.usecases.auth.tokens

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.GetAccountsWithExpiringAccessTokenUseCase
import org.koin.core.annotation.Factory

@Factory
class UpdateAccessTokensForAccountsUseCase(
    private val getAccountsWithExpiringAccessTokenUseCase: GetAccountsWithExpiringAccessTokenUseCase,
    private val updateAccessTokenForSteamAccountUseCase: UpdateAccessTokenForSteamAccountUseCase
) : BaseUseCase {

    suspend operator fun invoke(): Result<List<Result<Long>>> {
        return getAccountsWithExpiringAccessTokenUseCase.invoke().mapCatching { steamIds ->
            steamIds.mapConcurrency { steamId ->
                updateAccessTokenForSteamAccountUseCase.invoke(steamId)
            }
        }
    }
}
