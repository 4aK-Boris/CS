package dmitriy.losev.cs.usecases.auth.tokens

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.GetAccountsWithExpiringRefreshTokenUseCase
import org.koin.core.annotation.Factory

@Factory
class UpdateRefreshTokensForAccountsUseCase(
    private val getAccountsWithExpiringRefreshTokenUseCase: GetAccountsWithExpiringRefreshTokenUseCase,
    private val updateRefreshTokenForSteamAccountUseCase: UpdateRefreshTokenForSteamAccountUseCase
) : BaseUseCase {

    suspend operator fun invoke(): Result<List<Result<Long>>> {
        return getAccountsWithExpiringRefreshTokenUseCase.invoke().mapCatching { steamIds ->
            steamIds.mapConcurrency { steamId ->
                updateRefreshTokenForSteamAccountUseCase.invoke(steamId)
            }
        }
    }
}
