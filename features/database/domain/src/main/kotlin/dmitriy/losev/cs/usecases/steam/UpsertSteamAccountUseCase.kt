package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpsertSteamAccountUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamAccount: SteamAccountDTO): Result<UpsertSteamAccountResponseDTO> = runCatching {
        steamDatabaseAccountRepository.upsertSteamAccount(steamAccount).requireNotNull {
            "Error with upsert SteamAccount = $steamAccount, UpsertSteamAccountResponse is null"
        }
    }
}
