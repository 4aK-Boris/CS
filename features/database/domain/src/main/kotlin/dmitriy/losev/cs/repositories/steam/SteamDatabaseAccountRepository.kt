package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO

interface SteamDatabaseAccountRepository {

    suspend fun upsertSteamAccount(steamAccount: SteamAccountDTO): UpsertSteamAccountResponseDTO?

    suspend fun upsertActiveSteamAccount(activeSteamAccount: ActiveSteamAccountDTO): UpsertActiveSteamAccountResponseDTO?

    suspend fun deleteSteamAccount(steamId: Long): Long

    suspend fun deleteActiveSteamAccount(steamId: Long): Long

    suspend fun getSteamAccountBySteamId(steamId: Long): SteamAccountDTO?

    suspend fun getActiveSteamAccountBySteamId(steamId: Long): ActiveSteamAccountDTO?

    suspend fun getAllSteamAccounts(): List<SteamAccountDTO>

    suspend fun getAllActiveSteamAccounts(): List<ActiveSteamAccountDTO>

    suspend fun getAllActiveSteamAccountsSteamId(): List<Long>
}
