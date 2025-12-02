package dmitriy.losev.cs.usecases.steam

import dmitriy.losev.cs.dto.steam.ActiveSteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertActiveSteamAccountResponseDTO
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpsertActiveSteamAccountUseCase(@Provided private val steamDatabaseAccountRepository: SteamDatabaseAccountRepository) : BaseUseCase {

    suspend operator fun invoke(activeSteamAccount: ActiveSteamAccountDTO): Result<UpsertActiveSteamAccountResponseDTO> = runCatching {
        steamDatabaseAccountRepository.upsertActiveSteamAccount(activeSteamAccount).requireNotNull {
            "Error with upsert ActiveSteamAccount = $activeSteamAccount, UpsertActiveSteamAccountResponse is null"
        }
    }
}
