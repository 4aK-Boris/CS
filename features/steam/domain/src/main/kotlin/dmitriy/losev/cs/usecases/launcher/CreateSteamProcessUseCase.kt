package dmitriy.losev.cs.usecases.launcher

import dmitriy.losev.cs.repositories.SteamLauncherRepository
import dmitriy.losev.cs.usecases.BaseUseCase
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

//@Factory
//class CreateSteamProcessUseCase(@Provided private val steamLauncherRepository: SteamLauncherRepository): BaseUseCase {
//
//    suspend operator fun invoke(userName: String, index: Int): Result<Long> = runCatching {
//        steamLauncherRepository.createSteamProcess(userName, index)
//    }
//}