package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.pulse.SetPulseAuthTokenUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SetAuthTokenFromDataStoreUseCase(@Provided private val setPulseAuthTokenUseCase: SetPulseAuthTokenUseCase) : BaseUseCase {

    suspend operator fun invoke(authToken: String): Result<String> {
        return setPulseAuthTokenUseCase.invoke(authToken)
    }
}