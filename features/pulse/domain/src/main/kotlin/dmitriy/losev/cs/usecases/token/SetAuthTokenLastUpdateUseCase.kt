package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.pulse.SetPulseAuthTokenLastUpdateUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SetAuthTokenLastUpdateUseCase(@Provided private val setPulseAuthTokenLastUpdateUseCase: SetPulseAuthTokenLastUpdateUseCase) : BaseUseCase {

    suspend operator fun invoke(): Result<Unit> = runCatching {
        setPulseAuthTokenLastUpdateUseCase.invoke()
    }
}