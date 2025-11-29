package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.usecases.launcher.LaunchSteamWithAuthUseCase
import kotlinx.coroutines.delay
import org.koin.core.annotation.Factory

@Factory
class LoginInSteamAndOpenCSUseCase(
    private val getSteamCredentials: GetSteamCredentialsUseCase,
    private val launchSteamWithAuthUseCase: LaunchSteamWithAuthUseCase,
    private val findCSProcessIdAndKillSingletonMutexUseCase: FindCSProcessIdAndKillSingletonMutexUseCase
): BaseUseCase {

    suspend operator fun invoke(): Result<Unit> {
        return getSteamCredentials.invoke().mapCatching { usersCredentials ->
            usersCredentials.forEachIndexed { index, credentials ->
                val steamProcessId = launchSteamWithAuthUseCase.invoke(userName = credentials.login, password = credentials.password, index = index).getOrThrow()
                delay(10_000L)
                //findCSProcessIdAndKillSingletonMutexUseCase.invoke(steamPid = steamProcessId).getOrThrow()
            }
        }
    }
}