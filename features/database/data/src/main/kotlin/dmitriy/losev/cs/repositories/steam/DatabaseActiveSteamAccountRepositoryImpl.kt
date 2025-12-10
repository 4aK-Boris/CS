package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.AesCrypto
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
    @Provided private val aesCrypto: AesCrypto,
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

    override suspend fun getAccountsWithExpiringAccessToken(): List<Long> {
        return activeSteamAccountHandler.getAccountsWithExpiringAccessToken()
    }

    override suspend fun getAccountsWithExpiringRefreshToken(): List<Long> {
        return activeSteamAccountHandler.getAccountsWithExpiringRefreshToken()
    }

    override suspend fun getAccountsWithExpiringCsFloatToken(): List<Long> {
        return activeSteamAccountHandler.getAccountsWithExpiringCsFloatToken()
    }

    override suspend fun getAccountRefreshToken(steamId: Long): String? {
        return activeSteamAccountHandler.getAccountRefreshToken(steamId)?.let(block = aesCrypto::decrypt)
    }

    override suspend fun updateAccessToken(steamId: Long, accessToken: String) {
        activeSteamAccountHandler.updateAccessToken(steamId, accessToken)
    }

    override suspend fun updateRefreshToken(steamId: Long, accessToken: String, refreshToken: String) {
        updateAccessToken(steamId, accessToken)
        activeSteamAccountHandler.updateRefreshToken(steamId, refreshToken = aesCrypto.encrypt(data = refreshToken))
    }

    override suspend fun updateCsFloatToken(steamId: Long) {
        activeSteamAccountHandler.updateCsFloatToken(steamId)
    }
    
    private fun ActiveSteamAccountDSO.toDTO(): ActiveSteamAccountDTO = activeSteamAccountMapper.map(value = this)

    private fun ActiveSteamAccountDTO.toDSO(): ActiveSteamAccountDSO = activeSteamAccountMapper.map(value = this)

    private fun UpsertActiveSteamAccountResponseDSO.toDTO(): UpsertActiveSteamAccountResponseDTO = upsertActiveSteamAccountResponseMapper.map(value = this)
}
