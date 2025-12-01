package dmitriy.losev.cs.repositories

interface SteamLauncherRepository {

    suspend fun createSteamProcess(userName: String, index: Int): Long

    suspend fun createFileDirectoriesForUser(userName: String)

    suspend fun authWithLoginAndPassword(pid: Long, userName: String, password: String, isFirst: Boolean)

    suspend fun authWithSteamGuardCode(pid: Long, userName: String, steamGuardCode: String)

    suspend fun close(pid: Long)
}
