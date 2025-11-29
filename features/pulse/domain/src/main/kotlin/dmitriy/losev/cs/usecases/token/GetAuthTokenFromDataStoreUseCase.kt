package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.pulse.GetPulseAuthTokenUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenFromDataStoreUseCase(@Provided private val getPulseAuthTokenUseCase: GetPulseAuthTokenUseCase) : BaseUseCase {

    suspend operator fun invoke(): Result<String> {
        return getPulseAuthTokenUseCase.invoke()
    }
}