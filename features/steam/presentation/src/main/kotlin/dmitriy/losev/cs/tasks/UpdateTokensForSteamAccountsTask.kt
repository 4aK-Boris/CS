package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.usecases.auth.tokens.UpdateAccessTokensForAccountsUseCase
import dmitriy.losev.cs.usecases.auth.tokens.UpdateCSFloatTokensForAccountsUseCase
import dmitriy.losev.cs.usecases.auth.tokens.UpdateRefreshTokensForAccountsUseCase
import org.koin.core.annotation.Singleton

@Singleton
class UpdateTokensForSteamAccountsTask(
    private val updateAccessTokensForAccountsUseCase: UpdateAccessTokensForAccountsUseCase,
    private val updateRefreshTokensForAccountsUseCase: UpdateRefreshTokensForAccountsUseCase,
    private val updateCSFloatTokensForAccountsUseCase: UpdateCSFloatTokensForAccountsUseCase
) {

    suspend fun updateAccessTokensForSteamAccountsTask(): Result<List<Result<Long>>> {
        return updateAccessTokensForAccountsUseCase.invoke()
    }

    suspend fun updateRefreshTokensForSteamAccountsTask(): Result<List<Result<Long>>> {
        return updateRefreshTokensForAccountsUseCase.invoke()
    }

    suspend fun updateCsFloatTokensForSteamAccountsTask(): Result<List<Result<Long>>> {
        return updateCSFloatTokensForAccountsUseCase.invoke()
    }
}
