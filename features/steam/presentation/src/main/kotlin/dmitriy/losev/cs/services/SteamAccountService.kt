package dmitriy.losev.cs.services

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.mappers.UpsertSteamAccountRequestMapper
import dmitriy.losev.cs.mappers.UpsertSteamAccountResponseMapper
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import dmitriy.losev.cs.usecases.steam.UpsertSteamAccountUseCase
import org.koin.core.annotation.Single

@Single
class SteamAccountService(
    private val upsertSteamAccountUseCase: UpsertSteamAccountUseCase,

    private val upsertSteamAccountRequestMapper: UpsertSteamAccountRequestMapper,
    private val upsertSteamAccountResponseMapper: UpsertSteamAccountResponseMapper
    // другие UseCase при необходимости
    // private val getSteamAccountUseCase: GetSteamAccountUseCase,
    // private val deleteSteamAccountUseCase: DeleteSteamAccountUseCase
) {
    suspend fun upsertAccount(upsertSteamAccountRequest: UpsertSteamAccountRequestModel): Result<UpsertSteamAccountResponseModel> {
        return upsertSteamAccountUseCase.invoke(steamAccount = upsertSteamAccountRequest.toDTO()).map(upsertSteamAccountResponseMapper::map)

    }

    // Другие методы
    // suspend fun getAccount(steamId: Long): Result<SteamAccountResponse> { ... }
    // suspend fun deleteAccount(steamId: Long): Result<Unit> { ... }
    // suspend fun getAllAccounts(): Result<List<SteamAccountResponse>> { ... }

    private fun UpsertSteamAccountRequestModel.toDTO(): SteamAccountDTO = upsertSteamAccountRequestMapper.map(value = this)
}
