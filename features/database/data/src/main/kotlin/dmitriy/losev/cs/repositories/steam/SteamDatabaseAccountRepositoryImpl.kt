package dmitriy.losev.cs.repositories.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.handlers.steam.SteamAccountHandler
import dmitriy.losev.cs.mappers.steam.SteamAccountMapper
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [SteamDatabaseAccountRepository::class])
class SteamDatabaseAccountRepositoryImpl(
    @Provided private val steamAccountHandler: SteamAccountHandler,
    private val steamAccountMapper: SteamAccountMapper
) : SteamDatabaseAccountRepository {

    override suspend fun insertSteamAccount(steamAccountString: String): ULong {
        return steamAccountMapper.map(value = steamAccountString).apply {
            steamAccountHandler.insertSteamAccount(steamAccount = this)
        }.let { steamAccount ->
            steamAccountMapper.map(value = steamAccount)
        }.steamId
    }

    override suspend fun deleteSteamAccount(steamId: ULong) {
        steamAccountHandler.deleteSteamAccount(steamId)
    }

    override suspend fun getSteamAccountById(steamId: ULong): SteamAccountDTO? {
        return steamAccountHandler.getSteamAccountBySteamId(steamId)?.let { steamAccount -> steamAccountMapper.map(value = steamAccount) }
    }

    override suspend fun getAllSteamAccounts(): List<SteamAccountDTO> {
        return steamAccountHandler.getAllSteamAccounts().map(transform = steamAccountMapper::map)
    }
}