package dmitriy.losev.cs.services

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.mappers.UpsertActiveSteamAccountRequestMapper
import dmitriy.losev.cs.mappers.UpsertActiveSteamAccountResponseMapper
import dmitriy.losev.cs.mappers.UpsertSteamAccountRequestMapper
import dmitriy.losev.cs.mappers.UpsertSteamAccountResponseMapper
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import dmitriy.losev.cs.usecases.steam.UpsertActiveSteamAccountUseCase
import dmitriy.losev.cs.usecases.steam.UpsertSteamAccountUseCase
import org.koin.core.annotation.Single

@Single
class SteamAccountService(
    private val upsertSteamAccountUseCase: UpsertSteamAccountUseCase,
    private val upsertActiveSteamAccountUseCase: UpsertActiveSteamAccountUseCase,

    private val upsertSteamAccountRequestMapper: UpsertSteamAccountRequestMapper,
    private val upsertSteamAccountResponseMapper: UpsertSteamAccountResponseMapper,
    private val upsertActiveSteamAccountRequestMapper: UpsertActiveSteamAccountRequestMapper,
    private val upsertActiveSteamAccountResponseMapper: UpsertActiveSteamAccountResponseMapper
) {
    suspend fun upsertAccount(upsertSteamAccountRequest: UpsertSteamAccountRequestModel): Result<UpsertSteamAccountResponseModel> {
        return upsertSteamAccountUseCase.invoke(steamAccount = upsertSteamAccountRequest.toDTO()).map(upsertSteamAccountResponseMapper::map)
    }

    suspend fun upsertActiveAccount(upsertActiveSteamAccountRequest: UpsertActiveSteamAccountRequestModel): Result<UpsertActiveSteamAccountResponseModel> {
        return upsertActiveSteamAccountUseCase.invoke(activeSteamAccount = upsertActiveSteamAccountRequest.toDTO()).map(upsertActiveSteamAccountResponseMapper::map)
    }

    // Другие методы
    // suspend fun getAccount(steamId: Long): Result<SteamAccountResponse> { ... }
    // suspend fun deleteAccount(steamId: Long): Result<Unit> { ... }
    // suspend fun getAllAccounts(): Result<List<SteamAccountResponse>> { ... }

    private fun UpsertSteamAccountRequestModel.toDTO(): SteamAccountDTO = upsertSteamAccountRequestMapper.map(value = this)

    private fun UpsertActiveSteamAccountRequestModel.toDTO(): ActiveSteamAccountDTO = upsertActiveSteamAccountRequestMapper.map(value = this)
}
