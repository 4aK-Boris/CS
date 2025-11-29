package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.exceptions.BaseException
import dmitriy.losev.cs.repositories.CSProcessRepository
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory
class FindCSPidBySteamPidUseCase(@Provided private val csProcessRepository: CSProcessRepository): BaseUseCase {

    suspend operator fun invoke(steamPid: Int): Result<Int> = runCatching {
        csProcessRepository.findCSPidBySteamPid(steamPid) ?: throw BaseException.BaseProcessIdNotFoundException(steamPid)
    }
}