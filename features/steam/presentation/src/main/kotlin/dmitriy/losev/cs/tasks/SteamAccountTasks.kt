package dmitriy.losev.cs.tasks

import dmitriy.losev.cs.usecases.account.AddSteamAccountsUseCase
import dmitriy.losev.cs.usecases.throwException
import org.koin.core.annotation.Factory

@Factory
class SteamAccountTasks(
    private val addSteamAccountsUseCase: AddSteamAccountsUseCase
) {

    suspend fun startAddSteamAccountsTask() {
        addSteamAccountsUseCase.invoke(path = PATH).getOrThrow()
    }

    companion object {
        private const val PATH = "trade/src/main/resources/accounts"
    }
}