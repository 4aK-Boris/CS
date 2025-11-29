package dmitriy.losev.cs.usecases.guard

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.account.GetSteamAccountUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class GetSteamGuardCodeBySteamIdUseCase(
    private val getSteamAccountUseCase: GetSteamAccountUseCase,
    private val getSteamGuardCodeUseCase: GetSteamGuardCodeUseCase
): BaseUseCase {

    suspend operator fun invoke(steamId: ULong): Result<String>  {
        return getSteamAccountUseCase.invoke(steamId).mapCatchingInData { steamAccount ->
            getSteamGuardCodeUseCase.invoke(sharedSecret = steamAccount.sharedSecret)
        }
    }
}