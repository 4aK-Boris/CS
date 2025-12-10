package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dso.steam.SteamAccountCredentialsDSO
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.dto.steam.SteamAccountCredentialsDTO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.mappers.steam.SteamAccountCredentialsMapper
import dmitriy.losev.cs.mappers.steam.SteamAccountMapper
import dmitriy.losev.cs.mappers.steam.UpsertSteamAccountResponseMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [DatabaseSteamAccountRepository::class])
internal class DatabaseSteamAccountRepositoryImpl(
    @Provided private val steamAccountHandler: SteamAccountHandler,
    private val steamAccountMapper: SteamAccountMapper,
    private val upsertSteamAccountResponseMapper: UpsertSteamAccountResponseMapper,
    private val steamAccountCredentialsMapper: SteamAccountCredentialsMapper
) : DatabaseSteamAccountRepository {

    override suspend fun upsertSteamAccount(steamAccount: SteamAccountDTO): UpsertSteamAccountResponseDTO? {
        return steamAccountHandler.upsertSteamAccount(steamAccount.toDSO())?.toDTO()
    }

    override suspend fun deleteSteamAccountBySteamId(steamId: Long): Long? {
        return steamAccountHandler.deleteSteamAccountBySteamId(steamId)
    }

    override suspend fun deleteSteamAccountByLogin(login: String): Long? {
        return steamAccountHandler.deleteSteamAccountByLogin(login)
    }

    override suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDTO? {
        return steamAccountHandler.getSteamAccountBySteamId(steamId)?.toDTO()
    }

    override suspend fun getSteamAccountByLogin(login: String): SteamAccountDTO? {
        return steamAccountHandler.getSteamAccountByLogin(login)?.toDTO()
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDTO> {
        return steamAccountHandler.getAllSteamAccounts().map(transform = steamAccountMapper::map)
    }

    override suspend fun getSteamAccountCredentials(steamId: Long): SteamAccountCredentialsDTO? {
        return steamAccountHandler.getSteamAccountCredentials(steamId)?.toDTO()
    }

    private fun SteamAccountDSO.toDTO(): SteamAccountDTO = steamAccountMapper.map(value = this)

    private fun SteamAccountDTO.toDSO(): SteamAccountDSO = steamAccountMapper.map(value = this)

    private fun UpsertSteamAccountResponseDSO.toDTO(): UpsertSteamAccountResponseDTO = upsertSteamAccountResponseMapper.map(value = this)

    private fun SteamAccountCredentialsDSO.toDTO(): SteamAccountCredentialsDTO = steamAccountCredentialsMapper.map(value = this)
}
