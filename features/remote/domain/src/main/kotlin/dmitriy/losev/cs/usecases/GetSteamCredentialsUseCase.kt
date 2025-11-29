package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.dto.CredentialsDTO
import dmitriy.losev.cs.repositories.CredentialsRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class GetSteamCredentialsUseCase(@Provided private val credentialsRepository: CredentialsRepository): BaseUseCase {

    suspend operator fun invoke(): Result<List<CredentialsDTO>> = runCatching {
        credentialsRepository.getSteamCredentials()
    }
}