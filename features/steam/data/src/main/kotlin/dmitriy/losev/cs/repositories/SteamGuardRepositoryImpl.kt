package dmitriy.losev.cs.repositories

//@Factory(binds = [SteamGuardRepository::class])
//internal class SteamGuardRepositoryImpl(private val steamGuard: SteamGuard): SteamGuardRepository {
//
//    override suspend fun getSteamGuardCode(sharedSecret: String): String {
//        return steamGuard.generateSteamGuardCode(sharedSecret)
//    }
//
//    override suspend fun getConfirmationKey(identitySecret: String, tag: String): String {
//        return steamGuard.generateConfirmationKey(identitySecret, tag)
//    }
//
//    override suspend fun encryptPassword(password: String, modulus: String, exponent: String): String {
//        return steamGuard.encryptPassword(password, modulus, exponent)
//    }
//}
