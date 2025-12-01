package dmitriy.losev.cs.usecases.guard

//import dmitriy.losev.cs.repositories.SteamGuardRepository
//import dmitriy.losev.cs.usecases.BaseUseCase
//import org.koin.core.annotation.Factory
//import org.koin.core.annotation.Provided
//
//@Factory
//class EncryptPasswordUseCase(@Provided private val steamGuardRepository: SteamGuardRepository): BaseUseCase {
//
//    suspend operator fun invoke(password: String, modulus: String, exponent: String): Result<String> = runCatching {
//        steamGuardRepository.encryptPassword(password, modulus, exponent)
//    }
//}
