package dmitriy.losev.cs.services

import dmitriy.losev.cs.dto.UpsertActiveSteamAccountRequestDTO
import dmitriy.losev.cs.mappers.GetActiveSteamAccountResponseMapper
import dmitriy.losev.cs.mappers.UpsertActiveSteamAccountRequestMapper
import dmitriy.losev.cs.mappers.UpsertActiveSteamAccountResponseMapper
import dmitriy.losev.cs.models.GetActiveSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertActiveSteamAccountResponseModel
import dmitriy.losev.cs.usecases.account.UpsertActiveSteamAccountWithLoginUseCase
import dmitriy.losev.cs.usecases.steam.account.active.DeleteActiveSteamAccountByLoginUseCase
import dmitriy.losev.cs.usecases.steam.account.active.DeleteActiveSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.active.GetActiveSteamAccountByLoginUseCase
import dmitriy.losev.cs.usecases.steam.account.active.GetActiveSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.active.GetAllActiveSteamAccountsUseCase
import org.koin.core.annotation.Single

@Single
class ActiveSteamAccountService(
    private val upsertActiveSteamAccountWithLoginUseCase: UpsertActiveSteamAccountWithLoginUseCase,
    private val getActiveSteamAccountBySteamIdUseCase: GetActiveSteamAccountBySteamIdUseCase,
    private val getActiveSteamAccountByLoginUseCase: GetActiveSteamAccountByLoginUseCase,
    private val getAllActiveSteamAccountsUseCase: GetAllActiveSteamAccountsUseCase,
    private val deleteActiveSteamAccountBySteamIdUseCase: DeleteActiveSteamAccountBySteamIdUseCase,
    private val deleteActiveSteamAccountByLoginUseCase: DeleteActiveSteamAccountByLoginUseCase,
    private val upsertActiveSteamAccountRequestMapper: UpsertActiveSteamAccountRequestMapper,
    private val upsertActiveSteamAccountResponseMapper: UpsertActiveSteamAccountResponseMapper,
    private val getActiveSteamAccountResponseMapper: GetActiveSteamAccountResponseMapper
) {

    suspend fun upsertActiveSteamAccount(upsertActiveSteamAccountRequest: UpsertActiveSteamAccountRequestModel): Result<UpsertActiveSteamAccountResponseModel> {
        return upsertActiveSteamAccountWithLoginUseCase.invoke(upsertActiveSteamAccountRequest.toDTO()).map(upsertActiveSteamAccountResponseMapper::map)
    }

    suspend fun getActiveSteamAccountBySteamId(steamId: String): Result<GetActiveSteamAccountResponseModel> {
        return getActiveSteamAccountBySteamIdUseCase.invoke(steamId = steamId.toLong()).map(transform = getActiveSteamAccountResponseMapper::map)
    }

    suspend fun getActiveSteamAccountByLogin(login: String): Result<GetActiveSteamAccountResponseModel> {
        return getActiveSteamAccountByLoginUseCase.invoke(login).map(transform = getActiveSteamAccountResponseMapper::map)
    }

    suspend fun getAllActiveSteamAccounts(): Result<List<GetActiveSteamAccountResponseModel>> {
        return getAllActiveSteamAccountsUseCase.invoke().map { accounts -> accounts.map(transform = getActiveSteamAccountResponseMapper::map) }
    }

    suspend fun deleteSteamAccountBySteamId(steamId: String): Result<String> {
        return deleteActiveSteamAccountBySteamIdUseCase.invoke(steamId = steamId.toLong()).map(transform = Long::toString)
    }

    suspend fun deleteSteamAccountByLogin(login: String): Result<String> {
        return deleteActiveSteamAccountByLoginUseCase.invoke(login).map(transform = Long::toString)
    }

    private fun UpsertActiveSteamAccountRequestModel.toDTO(): UpsertActiveSteamAccountRequestDTO = upsertActiveSteamAccountRequestMapper.map(value = this)
}
