package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.SteamAccountCredentialsDTO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO

interface DatabaseSteamAccountRepository {

    suspend fun upsertSteamAccount(steamAccount: SteamAccountDTO): UpsertSteamAccountResponseDTO?

    suspend fun deleteSteamAccountBySteamId(steamId: Long): Long?

    suspend fun deleteSteamAccountByLogin(login: String): Long?

    suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDTO?

    suspend fun getSteamAccountByLogin(login: String): SteamAccountDTO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDTO>

    suspend fun getSteamAccountCredentials(steamId: Long): SteamAccountCredentialsDTO?

}
