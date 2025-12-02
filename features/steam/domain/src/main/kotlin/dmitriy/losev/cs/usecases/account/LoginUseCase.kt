package dmitriy.losev.cs.usecases.account

//@Factory
//class LoginUseCase(@Provided private val steamAccountRepository: SteamAccountRepository) : BaseUseCase {
//
//    suspend operator fun invoke(
//        steamId: Long,
//        login: String,
//        encryptedPassword: String,
//        steamGuardCode: String,
//        rsaKeyTimeStamp: Long
//    ): Result<SteamAccountCookiesDTO> = runCatching {
//        steamAccountRepository.login(steamId, login, encryptedPassword, steamGuardCode, rsaKeyTimeStamp)
//    }
//}
