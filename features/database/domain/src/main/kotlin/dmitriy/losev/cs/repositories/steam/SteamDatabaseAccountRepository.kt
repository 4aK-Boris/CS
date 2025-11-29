package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO

interface SteamDatabaseAccountRepository {

    suspend fun insertSteamAccount(steamAccountString: String): ULong

    suspend fun deleteSteamAccount(steamId: ULong)

    suspend fun getSteamAccountById(steamId: ULong): SteamAccountDTO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDTO>
}