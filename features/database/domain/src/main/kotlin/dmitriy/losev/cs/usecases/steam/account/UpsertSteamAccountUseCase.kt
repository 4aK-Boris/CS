package dmitriy.losev.cs.usecases.steam.account

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.dto.steam.UpsertSteamAccountResponseDTO
import dmitriy.losev.cs.repositories.steam.DatabaseSteamAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class UpsertSteamAccountUseCase(@Provided private val databaseSteamAccountRepository: DatabaseSteamAccountRepository) : BaseUseCase {

    suspend operator fun invoke(steamAccount: SteamAccountDTO): Result<UpsertSteamAccountResponseDTO> = runCatching {
        databaseSteamAccountRepository.upsertSteamAccount(steamAccount).requireNotNull {
            "Error with upsert SteamAccount = $steamAccount, UpsertSteamAccountResponse is null"
        }
    }
}
