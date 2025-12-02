package dmitriy.losev.cs.usecases

import dmitriy.losev.cs.usecases.proxy.AddSteamAccountProxyConfigUseCase
import dmitriy.losev.cs.usecases.proxy.DeleteProxyConfigUseCase
import dmitriy.losev.cs.usecases.proxy.GetSteamIdByProxyConfigUseCase
import dmitriy.losev.cs.usecases.proxy.HasAvailableProxyUseCase
import org.koin.core.annotation.Factory

@Factory
class DeleteProxyConfigWithSwapProxyUseCase(
    private val deleteProxyConfigUseCase: DeleteProxyConfigUseCase,
    private val hasAvailableProxyUseCase: HasAvailableProxyUseCase,
    private val getSteamIdByProxyConfigUseCase: GetSteamIdByProxyConfigUseCase,
    private val addSteamAccountProxyConfigUseCase: AddSteamAccountProxyConfigUseCase
): BaseUseCase {

    suspend operator fun invoke(host: String, port: Int): Result<Int> {

        val steamIdResult = getSteamIdByProxyConfigUseCase.invoke(host, port)

        return if (steamIdResult.isFailure && steamIdResult.exceptionOrNull() is IllegalArgumentException) {
            deleteProxyConfigUseCase.invoke(host, port)
        } else {
            hasAvailableProxyUseCase.invoke().mapCatching { hasAvailableProxy ->
                require(value = hasAvailableProxy.not()) { "There are no free proxies to switch your steam account" }
                addSteamAccountProxyConfigUseCase.invoke(steamId = steamIdResult.getOrThrow()).getOrThrow()
                deleteProxyConfigUseCase.invoke(host, port).getOrThrow()
            }
        }
    }
}
