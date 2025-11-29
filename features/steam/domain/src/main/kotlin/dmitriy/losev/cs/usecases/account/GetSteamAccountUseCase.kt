package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.dto.steam.SteamAccountDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.steam.GetSteamAccountBySteamIdUseCase
import org.koin.core.annotation.Factory

@Factory
class GetSteamAccountUseCase(private val getSteamAccountBySteamIdUseCase: GetSteamAccountBySteamIdUseCase): BaseUseCase {

    suspend operator fun invoke(steamId: ULong): Result<SteamAccountDTO> {
        return getSteamAccountBySteamIdUseCase.invoke(steamId)
    }
}