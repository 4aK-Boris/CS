package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.ConfirmationsResponseDTO
import dmitriy.losev.cs.repositories.SteamAccountRepository
import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetConfirmationsUseCase(@Provided private val steamAccountRepository: SteamAccountRepository): BaseUseCase {

    suspend operator fun invoke(steamId: ULong, deviceId: String, confirmationKey: String): Result<ConfirmationsResponseDTO> = runCatching {
        steamAccountRepository.getConfirmations(steamId, deviceId, confirmationKey)
    }
}