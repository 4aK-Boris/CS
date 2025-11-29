package dmitriy.losev.cs.usecases.launcher

import dmitriy.losev.cs.repositories.SteamLauncherRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

//@Factory
//class AuthWithLoginAndPasswordUseCase(@Provided private val steamLauncherRepository: SteamLauncherRepository): BaseUseCase {
//
//    suspend operator fun invoke(pid: Long, userName: String, password: String, isFirst: Boolean = false): Result<Unit> = runCatching {
//        steamLauncherRepository.authWithLoginAndPassword(pid, userName, password, isFirst)
//    }
//}