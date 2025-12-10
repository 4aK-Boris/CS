package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.UpsertActiveSteamAccountRequestDTO
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.auth.LoginAndSaveCookieUseCase
import dmitriy.losev.cs.usecases.auth.openid.AuthWithOpenIdInOtherServicesUseCase
import dmitriy.losev.cs.usecases.proxy.AddSteamAccountProxyConfigUseCase
import dmitriy.losev.cs.usecases.steam.account.active.DeleteActiveSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.active.GetActiveSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.active.UpsertActiveSteamAccountUseCase
import org.koin.core.annotation.Factory

@Factory
class UpsertActiveSteamAccountWithLoginUseCase(
    private val upsertActiveSteamAccountUseCase: UpsertActiveSteamAccountUseCase,
    private val getActiveSteamAccountBySteamIdUseCase: GetActiveSteamAccountBySteamIdUseCase,
    private val addSteamAccountProxyConfigUseCase: AddSteamAccountProxyConfigUseCase,
    private val loginAndSaveCookieUseCase: LoginAndSaveCookieUseCase,
    private val deleteActiveSteamAccountBySteamIdUseCase: DeleteActiveSteamAccountBySteamIdUseCase,
    private val authWithOpenIdInOtherServicesUseCase: AuthWithOpenIdInOtherServicesUseCase
) : BaseUseCase {

    suspend operator fun invoke(upsertActiveSteamAccountRequest: UpsertActiveSteamAccountRequestDTO): Result<UpsertActiveSteamAccountResponseDTO> {
        val accountResult = getActiveSteamAccountBySteamIdUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId)
        return if (accountResult.isSuccess) {
            val activeSteamAccount = accountResult.getOrThrow().copy(
                steamId = upsertActiveSteamAccountRequest.steamId,
                marketCSGOApiToken = upsertActiveSteamAccountRequest.marketCSGOApiToken
            )
            upsertActiveSteamAccountUseCase.invoke(activeSteamAccount)
        } else {
            val temporaryActiveSteamAccount = ActiveSteamAccountDTO(
                steamId = upsertActiveSteamAccountRequest.steamId,
                marketCSGOApiToken = upsertActiveSteamAccountRequest.marketCSGOApiToken
            )
            upsertActiveSteamAccountUseCase.invoke(activeSteamAccount = temporaryActiveSteamAccount).getOrThrow()
            addSteamAccountProxyConfigUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId).onFailure {
                deleteActiveSteamAccountBySteamIdUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId).getOrThrow()
            }.getOrThrow()
            loginAndSaveCookieUseCase.invoke(upsertActiveSteamAccountRequest).mapCatching { upsertActiveSteamAccountResponse ->
                authWithOpenIdInOtherServicesUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId).getOrThrow()
                upsertActiveSteamAccountResponse
            }.onFailure { throwable ->
                throwable.printStackTrace()
                deleteActiveSteamAccountBySteamIdUseCase.invoke(steamId = upsertActiveSteamAccountRequest.steamId).getOrThrow()
            }
        }
    }
}
