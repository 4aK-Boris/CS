package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.handlers.steam.ActiveSteamAccountHandler
import dmitriy.losev.cs.mappers.steam.ActiveSteamAccountMapper
import dmitriy.losev.cs.mappers.steam.UpsertActiveSteamAccountResponseMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [DatabaseActiveSteamAccountRepository::class])
internal class DatabaseActiveSteamAccountRepositoryImpl(
    @Provided private val activeSteamAccountHandler: ActiveSteamAccountHandler,
    private val activeSteamAccountMapper: ActiveSteamAccountMapper,
    private val upsertActiveSteamAccountResponseMapper: UpsertActiveSteamAccountResponseMapper
) : DatabaseActiveSteamAccountRepository {

    override suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDTO): UpsertActiveSteamAccountResponseDTO? {
        return activeSteamAccountHandler.upsertActiveSteamAccount(activeSteamAccount.toDSO())?.toDTO()
    }

    override suspend fun deleteActiveSteamAccountBySteamId(steamId: Long): Long? {
        return activeSteamAccountHandler.deleteActiveSteamAccountBySteamId(steamId)
    }

    override suspend fun deleteActiveSteamAccountByLogin(login: String): Long? {
        return activeSteamAccountHandler.deleteActiveSteamAccountByLogin(login)
    }

    override suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDTO? {
        return activeSteamAccountHandler.getActiveSteamAccountBySteamId(steamId)?.toDTO()
    }

    override suspend fun getActiveSteamAccountByLogin(login: String): ActiveSteamAccountDTO? {
        return activeSteamAccountHandler.getActiveSteamAccountByLogin(login)?.toDTO()
    }

    override suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDTO> {
        return activeSteamAccountHandler.getAllActiveSteamAccounts().map(transform = activeSteamAccountMapper::map)
    }

    override suspend fun getAllActiveSteamAccountsSteamId(): List<Long> {
        return activeSteamAccountHandler.getAllActiveSteamAccountsSteamId()
    }
    
    private fun ActiveSteamAccountDSO.toDTO(): ActiveSteamAccountDTO = activeSteamAccountMapper.map(value = this)

    private fun ActiveSteamAccountDTO.toDSO(): ActiveSteamAccountDSO = activeSteamAccountMapper.map(value = this)

    private fun UpsertActiveSteamAccountResponseDSO.toDTO(): UpsertActiveSteamAccountResponseDTO = upsertActiveSteamAccountResponseMapper.map(value = this)
}
