package dmitriy.losev.cs.usecases.token

import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.pulse.GetPulseAuthTokenLastUpdateUseCase
import java.time.LocalDateTime
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetAuthTokenLastUpdateUseCase(@Provided private val getPulseAuthTokenLastUpdateUseCase: GetPulseAuthTokenLastUpdateUseCase) : BaseUseCase {

    suspend operator fun invoke(): Result<LocalDateTime> {
        return getPulseAuthTokenLastUpdateUseCase.invoke()
    }
}