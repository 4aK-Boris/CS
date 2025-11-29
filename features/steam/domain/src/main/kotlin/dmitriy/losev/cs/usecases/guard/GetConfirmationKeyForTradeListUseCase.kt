package dmitriy.losev.cs.usecases.guard

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.account.GetSteamAccountUseCase
import dmitriy.losev.cs.usecases.mapCatchingInData
import org.koin.core.annotation.Factory

@Factory
class GetConfirmationKeyForTradeListUseCase(private val getConfirmationKeyBySteamIdUseCase: GetConfirmationKeyBySteamIdUseCase): BaseUseCase {

    suspend operator fun invoke(steamId: ULong): Result<String> {
        return getConfirmationKeyBySteamIdUseCase.invoke(steamId = steamId, tag = TAG)
    }

    companion object {
        private const val TAG = "conf"
    }
}