package dmitriy.losev.cs.handlers.steam

import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO

internal interface ActiveSteamAccountHandler {

    suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDSO): UpsertActiveSteamAccountResponseDSO?

    suspend fun deleteActiveSteamAccountBySteamId(steamId: Long): Long?

    suspend fun deleteActiveSteamAccountByLogin(login: String): Long?

    suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDSO?

    suspend fun getActiveSteamAccountByLogin(login: String): ActiveSteamAccountDSO?

    suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDSO>

    suspend fun getAllActiveSteamAccountsSteamId(): List<Long>

    suspend fun getAccountsWithExpiringAccessToken(): List<Long>

    suspend fun getAccountsWithExpiringRefreshToken(): List<Long>

    suspend fun getAccountsWithExpiringCsFloatToken(): List<Long>

    suspend fun getAccountRefreshToken(steamId: Long): ByteArray?

    suspend fun updateAccessToken(steamId: Long, accessToken: String)

    suspend fun updateRefreshToken(steamId: Long, refreshToken: ByteArray)

    suspend fun updateCsFloatToken(steamId: Long)
}
