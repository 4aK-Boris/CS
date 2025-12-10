package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO

interface DatabaseActiveSteamAccountRepository {

    suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDTO): UpsertActiveSteamAccountResponseDTO?

    suspend fun deleteActiveSteamAccountBySteamId(steamId: Long): Long?

    suspend fun deleteActiveSteamAccountByLogin(login: String): Long?

    suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDTO?

    suspend fun getActiveSteamAccountByLogin(login: String): ActiveSteamAccountDTO?

    suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDTO>

    suspend fun getAllActiveSteamAccountsSteamId(): List<Long>

    suspend fun getAccountsWithExpiringAccessToken(): List<Long>

    suspend fun getAccountsWithExpiringRefreshToken(): List<Long>

    suspend fun getAccountsWithExpiringCsFloatToken(): List<Long>

    suspend fun getAccountRefreshToken(steamId: Long): String?

    suspend fun updateAccessToken(steamId: Long, accessToken: String)

    suspend fun updateRefreshToken(steamId: Long, accessToken: String, refreshToken: String)

    suspend fun updateCsFloatToken(steamId: Long)
}
