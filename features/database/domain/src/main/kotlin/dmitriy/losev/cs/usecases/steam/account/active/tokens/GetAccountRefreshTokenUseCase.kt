package dmitriy.losev.cs.usecases.steam.account.active.tokens

import dmitriy.losev.cs.repositories.steam.DatabaseActiveSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAccountRefreshTokenUseCase(@Provided private val databaseActiveSteamAccountRepository: DatabaseActiveSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<String> = runCatching {
        databaseActiveSteamAccountRepository.getAccountRefreshToken(steamId) ?: error("Couldn't find refresh token for active steam account with SteamId $steamId")
    }
}
