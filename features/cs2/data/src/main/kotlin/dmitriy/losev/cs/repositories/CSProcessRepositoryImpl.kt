package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.mutex.MutexHandler
import dmitriy.losev.cs.process.ProcessHandler
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Provided

@Factory(binds = [CSProcessRepository::class])
class CSProcessRepositoryImpl(
    @Provided private val processHandler: ProcessHandler,
    @Provided private val mutexHandler: MutexHandler
): CSProcessRepository {

    override suspend fun findCSPidBySteamPid(steamPid: Int): Int? {
        return null//processHandler.findCS2FromSteam(steamPid)
    }

    override suspend fun killCSSingletonMutex(csProcessId: Int) {
        mutexHandler.killMutexesInProcess(processId = csProcessId, mutexFilter = CS2_SINGLETON_MUTEX_NAME)
    }

    companion object {
        private const val CS2_SINGLETON_MUTEX_NAME = "csgo_singleton_mutex"
    }
}