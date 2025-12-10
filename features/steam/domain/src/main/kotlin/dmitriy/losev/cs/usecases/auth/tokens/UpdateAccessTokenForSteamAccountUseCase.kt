package dmitriy.losev.cs.usecases.auth.tokens

import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppRequestDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.auth.GenerateAccessTokenForAppUseCase
import dmitriy.losev.cs.usecases.auth.GenerateAndSaveSteamLoginSecureCookieUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.GetAccountRefreshTokenUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.UpdateSteamAccountAccessTokenUseCase
import dmitriy.losev.cs.usecases.throwException
import org.koin.core.annotation.Factory

@Factory
class UpdateAccessTokenForSteamAccountUseCase(
    private val getAccountRefreshTokenUseCase: GetAccountRefreshTokenUseCase,
    private val updateSteamAccountAccessTokenUseCase: UpdateSteamAccountAccessTokenUseCase,
    private val generateAccessTokenForAppUseCase: GenerateAccessTokenForAppUseCase,
    private val generateAndSaveSteamLoginSecureCookieUseCase: GenerateAndSaveSteamLoginSecureCookieUseCase
): BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<Long> {
        return getAccountRefreshTokenUseCase.invoke(steamId).mapCatching { refreshToken ->
            val generateAccessTokenForAppRequest = GenerateAccessTokenForAppRequestDTO(steamId, refreshToken)
            val generateAccessTokenForAppResponse = generateAccessTokenForAppUseCase.invoke(generateAccessTokenForAppRequest).getOrThrow()
            updateSteamAccountAccessTokenUseCase.invoke(steamId = steamId, accessToken = generateAccessTokenForAppResponse.accessToken).throwException()
            generateAndSaveSteamLoginSecureCookieUseCase.invoke(steamId = steamId, accessToken = generateAccessTokenForAppResponse.accessToken).throwException()
            steamId
        }
    }
}
