package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.mappers.steam.ActiveSteamAccountMapper
import dmitriy.losev.cs.mappers.steam.SteamAccountMapper
import dmitriy.losev.cs.mappers.steam.UpsertActiveSteamAccountResponseMapper
import dmitriy.losev.cs.mappers.steam.UpsertSteamAccountResponseMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [SteamDatabaseAccountRepository::class])
internal class SteamDatabaseAccountRepositoryImpl(
    @Provided private val steamAccountHandler: SteamAccountHandler,
    private val steamAccountMapper: SteamAccountMapper,
    private val activeSteamAccountMapper: ActiveSteamAccountMapper,
    private val upsertSteamAccountResponseMapper: UpsertSteamAccountResponseMapper,
    private val upsertActiveSteamAccountResponseMapper: UpsertActiveSteamAccountResponseMapper
) : SteamDatabaseAccountRepository {

    override suspend fun upsertSteamAccount(steamAccount: SteamAccountDTO): UpsertSteamAccountResponseDTO? {
        return steamAccountHandler.upsertSteamAccount(steamAccount.toDSO())?.toDTO()
    }

    override suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDTO): UpsertActiveSteamAccountResponseDTO? {
        return steamAccountHandler.upsertActiveSteamAccount(activeSteamAccount.toDSO())?.toDTO()
    }

    override suspend fun deleteSteamAccount(steamId: Long): Long {
        steamAccountHandler.deleteSteamAccount(steamId)
        return steamId
    }

    override suspend fun deleteActiveSteamAccount(steamId: Long): Long {
        steamAccountHandler.deleteActiveSteamAccount(steamId)
        return steamId
    }

    override suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDTO? {
        return steamAccountHandler.getSteamAccountBySteamId(steamId)?.toDTO()
    }

    override suspend fun getSteamAccountByLogin(login: String): SteamAccountDTO? {
        return steamAccountHandler.getSteamAccountByLogin(login)?.toDTO()
    }

    override suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDTO? {
        return steamAccountHandler.getActiveSteamAccountBySteamId(steamId)?.toDTO()
    }

    override suspend fun getActiveSteamAccountByLogin(login: String): ActiveSteamAccountDTO? {
        return steamAccountHandler.getActiveSteamAccountByLogin(login)?.toDTO()
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDTO> {
        return steamAccountHandler.getAllSteamAccounts().map(transform = steamAccountMapper::map)
    }

    override suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDTO> {
        return steamAccountHandler.getAllActiveSteamAccounts().map(transform = activeSteamAccountMapper::map)
    }

    override suspend fun getAllActiveSteamAccountsSteamId(): List<Long> {
        return steamAccountHandler.getAllActiveSteamAccountsSteamId()
    }

    private fun SteamAccountDSO.toDTO(): SteamAccountDTO = steamAccountMapper.map(value = this)

    private fun SteamAccountDTO.toDSO(): SteamAccountDSO = steamAccountMapper.map(value = this)

    private fun ActiveSteamAccountDSO.toDTO(): ActiveSteamAccountDTO = activeSteamAccountMapper.map(value = this)

    private fun ActiveSteamAccountDTO.toDSO(): ActiveSteamAccountDSO = activeSteamAccountMapper.map(value = this)

    private fun UpsertSteamAccountResponseDSO.toDTO(): UpsertSteamAccountResponseDTO = upsertSteamAccountResponseMapper.map(value = this)

    private fun UpsertActiveSteamAccountResponseDSO.toDTO(): UpsertActiveSteamAccountResponseDTO = upsertActiveSteamAccountResponseMapper.map(value = this)
}
