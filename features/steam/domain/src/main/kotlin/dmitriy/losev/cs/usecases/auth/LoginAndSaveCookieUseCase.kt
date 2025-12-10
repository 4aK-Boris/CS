package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.UpsertActiveSteamAccountRequestDTO
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.guard.GenerateSessionIdUseCase
import dmitriy.losev.cs.usecases.steam.account.GetSteamAccountCredentialsUseCase
import dmitriy.losev.cs.usecases.steam.account.active.UpsertActiveSteamAccountUseCase
import dmitriy.losev.cs.usecases.throwException
import org.koin.core.annotation.Factory

@Factory
class LoginAndSaveCookieUseCase(
    private val getSteamAccountCredentialsUseCase: GetSteamAccountCredentialsUseCase,
    private val getAccessAndRefreshTokensForSteamAccountUseCase: GetAccessAndRefreshTokensForSteamAccountUseCase,
    private val generateAndSaveSteamCookieUseCase: GenerateAndSaveSteamCookieUseCase,
    private val generateSessionIdUseCase: GenerateSessionIdUseCase,
    private val upsertActiveSteamAccountUseCase: UpsertActiveSteamAccountUseCase
): BaseUseCase {

    suspend operator fun invoke(upsertActiveSteamAccountRequest: UpsertActiveSteamAccountRequestDTO): Result<UpsertActiveSteamAccountResponseDTO> {
        return getSteamAccountCredentialsUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId).mapCatching { (steamId, login, password, sharedSecret) ->
            val (accessToken, refreshToken) = getAccessAndRefreshTokensForSteamAccountUseCase.invoke(steamId, login, password, sharedSecret).getOrThrow()
            val sessionId = generateSessionIdUseCase.invoke().getOrThrow()
            val activeSteamAccount = ActiveSteamAccountDTO(
                steamId = upsertActiveSteamAccountRequest.steamId,
                marketCSGOApiToken = upsertActiveSteamAccountRequest.marketCSGOApiToken,
                accessToken = accessToken,
                refreshToken = refreshToken,
                sessionId = sessionId
            )
            upsertActiveSteamAccountUseCase.invoke(activeSteamAccount).getOrThrow().apply {
                generateAndSaveSteamCookieUseCase.invoke(steamId, accessToken, sessionId).throwException()
            }
        }

    }
}
