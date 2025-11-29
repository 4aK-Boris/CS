package dmitriy.losev.cs.handlers.steam

import dmitriy.losev.cs.dso.steam.SteamAccountDSO

interface SteamAccountHandler {

    suspend fun insertSteamAccount(steamAccount: SteamAccountDSO)

    suspend fun deleteSteamAccount(steamId: ULong)

    suspend fun getSteamAccountBySteamId(steamId: ULong): SteamAccountDSO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDSO>
}