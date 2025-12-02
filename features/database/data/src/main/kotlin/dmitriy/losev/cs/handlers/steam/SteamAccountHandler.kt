package dmitriy.losev.cs.handlers.steam

import dmitriy.losev.cs.dso.steam.ActiveSteamAccountDSO
import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertActiveSteamAccountResponseDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO

internal interface SteamAccountHandler {

    suspend fun upsertSteamAccount(steamAccount: SteamAccountDSO): UpsertSteamAccountResponseDSO?

    suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDSO): UpsertActiveSteamAccountResponseDSO?

    suspend fun deleteSteamAccount(steamId: Long)

    suspend fun deleteActiveSteamAccount(steamId: Long)

    suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDSO?

    suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDSO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDSO>

    suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDSO>

    suspend fun getAllActiveSteamAccountsSteamId(): List<Long>
}
