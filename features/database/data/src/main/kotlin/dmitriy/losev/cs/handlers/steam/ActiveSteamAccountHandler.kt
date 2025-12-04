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
}
