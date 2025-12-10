package dmitriy.losev.cs.usecases.auth.tokens

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.auth.GenerateAndSaveSteamLoginSecureCookieUseCase
import dmitriy.losev.cs.usecases.auth.GetAccessAndRefreshTokensForSteamAccountUseCase
import dmitriy.losev.cs.usecases.steam.account.GetSteamAccountCredentialsUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.UpdateSteamAccountRefreshTokenUseCase
import org.koin.core.annotation.Factory

@Factory
class UpdateRefreshTokenForSteamAccountUseCase(
    private val getSteamAccountCredentialsUseCase: GetSteamAccountCredentialsUseCase,
    private val getAccessAndRefreshTokensForSteamAccountUseCase: GetAccessAndRefreshTokensForSteamAccountUseCase,
    private val updateSteamAccountRefreshTokenUseCase: UpdateSteamAccountRefreshTokenUseCase,
    private val generateAndSaveSteamLoginSecureCookieUseCase: GenerateAndSaveSteamLoginSecureCookieUseCase
): BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<Long> {
        return getSteamAccountCredentialsUseCase.invoke(steamId).mapCatching { (steamId, login, password, sharedSecret) ->
            val (accessToken, refreshToken) = getAccessAndRefreshTokensForSteamAccountUseCase.invoke(steamId, login, password, sharedSecret).getOrThrow()
            updateSteamAccountRefreshTokenUseCase.invoke(steamId, accessToken, refreshToken).getOrThrow()
            generateAndSaveSteamLoginSecureCookieUseCase.invoke(steamId, accessToken)
            steamId
        }
    }
}
