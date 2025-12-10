package dmitriy.losev.cs.repositories

interface SteamGuardRepository {

    suspend fun getSteamGuardCode(sharedSecret: String): String

    suspend fun getConfirmationKey(identitySecret: String, tag: String): String

    suspend fun encryptPassword(password: String, modulus: String, exponent: String): String

    suspend fun generateSessionId(): String
}
