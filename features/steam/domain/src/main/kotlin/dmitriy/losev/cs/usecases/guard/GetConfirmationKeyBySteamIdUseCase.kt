package dmitriy.losev.cs.usecases.guard

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.account.GetSteamAccountUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class GetConfirmationKeyBySteamIdUseCase(
    private val getSteamAccountUseCase: GetSteamAccountUseCase,
    private val getConfirmationKeyUseCase: GetConfirmationKeyUseCase
): BaseUseCase {

    suspend operator fun invoke(steamId: ULong, tag: String): Result<String> {
        return getSteamAccountUseCase.invoke(steamId).mapCatchingInData { steamAccount ->
            getConfirmationKeyUseCase.invoke(identitySecret = steamAccount.identitySecret, tag = tag)
        }
    }
}