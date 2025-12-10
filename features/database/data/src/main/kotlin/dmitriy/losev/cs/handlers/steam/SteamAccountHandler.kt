package dmitriy.losev.cs.handlers.steam

import dmitriy.losev.cs.dso.steam.SteamAccountDSO
import dmitriy.losev.cs.dso.steam.UpsertSteamAccountResponseDSO
import dmitriy.losev.cs.dso.steam.SteamAccountCredentialsDSO

internal interface SteamAccountHandler {

    suspend fun upsertSteamAccount(steamAccount: SteamAccountDSO): UpsertSteamAccountResponseDSO?

    suspend fun deleteSteamAccountBySteamId(steamId: Long): Long?

    suspend fun deleteSteamAccountByLogin(login: String): Long?

    suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDSO?

    suspend fun getSteamAccountByLogin(login: String): SteamAccountDSO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDSO>

    suspend fun getSteamAccountCredentials(steamId: Long): SteamAccountCredentialsDSO?
}
