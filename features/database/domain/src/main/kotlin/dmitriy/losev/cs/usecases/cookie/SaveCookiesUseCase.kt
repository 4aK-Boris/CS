package dmitriy.losev.cs.usecases.cookie

import dmitriy.losev.cs.dto.cookie.NetworkCookieDTO
import dmitriy.losev.cs.repositories.cookie.DatabaseCookieRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class SaveCookiesUseCase(@Provided private val databaseCookieRepository: DatabaseCookieRepository): BaseUseCase {

    suspend operator fun invoke(cookies: List<NetworkCookieDTO>): Result<Unit> = runCatching {
        databaseCookieRepository.saveCookies(cookies)
    }
}
