package dmitriy.losev.cs.usecases.account

//@Factory
//class GetSteamAccountCookiesWithLoginUseCase(
//    private val encryptPasswordUseCase: EncryptPasswordUseCase,
//    private val getSteamGuardCodeUseCase: GetSteamGuardCodeUseCase,
//    private val getSteamAccountCookiesUseCase: GetSteamAccountCookiesUseCase,
//    private val insertSteamAccountCookiesUseCase: UpsertSteamAccountCookiesUseCase,
//    private val getRSAKeyUseCase: GetRSAKeyUseCase,
//    private val loginUseCase: LoginUseCase
//) {
//
//    suspend operator fun invoke(steamId: ULong, login: String, password: String, sharedSecret: String): Result<SteamAccountCookiesDTO> {
//        return getSteamAccountCookiesUseCase.invoke(steamId).mapCatching { steamAccountCookies ->
//            if (steamAccountCookies.expires.isBefore(currentDateTime)) {
//                throw SteamException.SteamAccountCookiesIsRottenException(steamId)
//            } else {
//                steamAccountCookies
//            }
//        }.recoverCatching {
//            val rsaKey = getRSAKeyUseCase.invoke(steamId, login).getOrThrow()
//            val steamGuardCode = getSteamGuardCodeUseCase.invoke(sharedSecret).getOrThrow()
//            val encryptPassword = encryptPasswordUseCase.invoke(password = password, modulus = rsaKey.publickeyModulus, exponent = rsaKey.publickeyExponent).getOrThrow()
//            val steamAccountCookies = loginUseCase.invoke(
//                steamId = steamId,
//                login = login,
//                encryptedPassword = encryptPassword,
//                steamGuardCode = steamGuardCode,
//                rsaKeyTimeStamp = rsaKey.timeStamp
//            ).getOrThrow()
//            insertSteamAccountCookiesUseCase.invoke(steamAccountCookies).getOrThrow()
//        }
//    }
//
//    private val currentDateTime: LocalDateTime
//        get() = LocalDateTime.now()
//}
