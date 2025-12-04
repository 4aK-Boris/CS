package dmitriy.losev.cs.services

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.mappers.GetSteamAccountResponseMapper
import dmitriy.losev.cs.mappers.UpsertSteamAccountRequestMapper
import dmitriy.losev.cs.mappers.UpsertSteamAccountResponseMapper
import dmitriy.losev.cs.models.GetSteamAccountResponseModel
import dmitriy.losev.cs.models.UpsertSteamAccountRequestModel
import dmitriy.losev.cs.models.UpsertSteamAccountResponseModel
import dmitriy.losev.cs.usecases.steam.account.DeleteSteamAccountByLoginUseCase
import dmitriy.losev.cs.usecases.steam.account.DeleteSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.GetAllSteamAccountsUseCase
import dmitriy.losev.cs.usecases.steam.account.GetSteamAccountByLoginUseCase
import dmitriy.losev.cs.usecases.steam.account.GetSteamAccountBySteamIdUseCase
import dmitriy.losev.cs.usecases.steam.account.UpsertSteamAccountUseCase
import org.koin.core.annotation.Single

@Single
class SteamAccountService(
    private val upsertSteamAccountUseCase: UpsertSteamAccountUseCase,
    private val getSteamAccountBySteamIdUseCase: GetSteamAccountBySteamIdUseCase,
    private val getSteamAccountByLoginUseCase: GetSteamAccountByLoginUseCase,
    private val getAllSteamAccountsUseCase: GetAllSteamAccountsUseCase,
    private val deleteSteamAccountBySteamIdUseCase: DeleteSteamAccountBySteamIdUseCase,
    private val deleteSteamAccountByLoginUseCase: DeleteSteamAccountByLoginUseCase,
    private val upsertSteamAccountRequestMapper: UpsertSteamAccountRequestMapper,
    private val upsertSteamAccountResponseMapper: UpsertSteamAccountResponseMapper,
    private val getSteamAccountResponseMapper: GetSteamAccountResponseMapper,
) {

    suspend fun upsertSteamAccount(upsertSteamAccountRequest: UpsertSteamAccountRequestModel): Result<UpsertSteamAccountResponseModel> {
        return upsertSteamAccountUseCase.invoke(steamAccount = upsertSteamAccountRequest.toDTO()).map(upsertSteamAccountResponseMapper::map)
    }

    suspend fun getSteamAccountBySteamId(steamId: String): Result<GetSteamAccountResponseModel> {
        return getSteamAccountBySteamIdUseCase.invoke(steamId = steamId.toLong()).map(transform = getSteamAccountResponseMapper::map)
    }

    suspend fun getSteamAccountByLogin(login: String): Result<GetSteamAccountResponseModel> {
        return getSteamAccountByLoginUseCase.invoke(login).map(transform = getSteamAccountResponseMapper::map)
    }

    suspend fun getAllSteamAccounts(): Result<List<GetSteamAccountResponseModel>> {
        return getAllSteamAccountsUseCase.invoke().map { accounts -> accounts.map(transform = getSteamAccountResponseMapper::map) }
    }

    suspend fun deleteSteamAccountBySteamId(steamId: String): Result<String> {
        return deleteSteamAccountBySteamIdUseCase.invoke(steamId = steamId.toLong()).map(transform = Long::toString)
    }

    suspend fun deleteSteamAccountByLogin(login: String): Result<String> {
        return deleteSteamAccountByLoginUseCase.invoke(login).map(transform = Long::toString)
    }

    private fun UpsertSteamAccountRequestModel.toDTO(): SteamAccountDTO = upsertSteamAccountRequestMapper.map(value = this)
}
