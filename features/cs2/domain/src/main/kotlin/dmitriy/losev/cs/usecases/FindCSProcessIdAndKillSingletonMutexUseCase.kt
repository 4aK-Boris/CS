package dmitriy.losev.cs.usecases

import org.koin.core.annotation.Factory

@Factory
class FindCSProcessIdAndKillSingletonMutexUseCase(
    private val findCSPidBySteamPidUseCase: FindCSPidBySteamPidUseCase,
    private val killCSSingletonMutexUseCase: KillCSSingletonMutexUseCase
): BaseUseCase {

    suspend operator fun invoke(steamPid: Int): Result<Unit> {
        return findCSPidBySteamPidUseCase.invoke(steamPid).mapCatchingInData { csProcessId ->
            killCSSingletonMutexUseCase.invoke(csProcessId)
        }
    }
}