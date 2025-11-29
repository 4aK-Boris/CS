package dmitriy.losev.cs.usecases.launcher

import dmitriy.losev.cs.repositories.SteamLauncherRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

//@Factory
//class AuthWithSteamGuardCodeUseCase(@Provided private val steamLauncherRepository: SteamLauncherRepository): BaseUseCase {
//
//    suspend operator fun invoke(pid: Long, userName: String, steamGuardCode: String): Result<Unit> = runCatching {
//        steamLauncherRepository.authWithSteamGuardCode(pid, userName, steamGuardCode)
//    }
//}