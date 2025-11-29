package dmitriy.losev.cs.usecases.account

import dmitriy.losev.cs.repositories.LisSkinsAccountRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetUserBalanceUseCase(@Provided private val lisSkinsAccountRepository: LisSkinsAccountRepository): BaseUseCase {

    suspend operator fun invoke(): Result<Double> = runCatching {
        lisSkinsAccountRepository.getUserBalance().balance
    }
}