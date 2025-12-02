package dmitriy.losev.cs.usecases.account

//import dmitriy.losev.cs.dto.RSAKeyDTO
//import dmitriy.losev.cs.repositories.SteamAccountRepository
//import dmitriy.losev.cs.repositories.steam.SteamDatabaseAccountRepository
//import dmitriy.losev.cs.usecases.BaseUseCase
//import org.koin.core.annotation.Factory
//import org.koin.core.annotation.Provided
//
//@Factory
//class GetRSAKeyUseCase(@Provided private val steamAccountRepository: SteamAccountRepository): BaseUseCase {
//
//    suspend operator fun invoke(steamId: Long, login: String): Result<RSAKeyDTO> = runCatching {
//        steamAccountRepository.getRSAKey(steamId, login)
//    }
//}
