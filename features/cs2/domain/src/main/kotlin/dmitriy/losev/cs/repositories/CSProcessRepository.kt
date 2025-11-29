package dmitriy.losev.cs.repositories

interface CSProcessRepository {

    suspend fun findCSPidBySteamPid(steamPid: Int): Int?

    suspend fun killCSSingletonMutex(csProcessId: Int)
}