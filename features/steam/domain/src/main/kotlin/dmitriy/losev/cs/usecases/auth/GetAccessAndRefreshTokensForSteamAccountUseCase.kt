package dmitriy.losev.cs.usecases.auth

import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsRequestDTO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusRequestDTO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeRequestDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.guard.GetSteamGuardCodeUseCase
import org.koin.core.annotation.Factory

@Factory
class GetAccessAndRefreshTokensForSteamAccountUseCase(
    private val getEncryptedPasswordForAccountUseCase: EncryptedPasswordForAccountUseCase,
    private val beginAuthSessionViaCredentialsUseCase: BeginAuthSessionViaCredentialsUseCase,
    private val updateAuthSessionWithSteamGuardCodeUseCase: UpdateAuthSessionWithSteamGuardCodeUseCase,
    private val getSteamGuardCodeUseCase: GetSteamGuardCodeUseCase,
    private val pollAuthSessionStatusUseCase: PollAuthSessionStatusUseCase
) : BaseUseCase {

    suspend operator fun invoke(steamId: Long, login: String, password: String, sharedSecret: String): Result<Pair<String, String>> = runCatching {
        val (encryptedPassword, keyTimeStamp) = getEncryptedPasswordForAccountUseCase.invoke(steamId, login, password).getOrThrow()
        val beginAuthSessionViaCredentialsRequest = BeginAuthSessionViaCredentialsRequestDTO(steamId, login, encryptedPassword, keyTimeStamp)
        val beginAuthSessionViaCredentialsResponse = beginAuthSessionViaCredentialsUseCase.invoke(beginAuthSessionViaCredentialsRequest).getOrThrow()
        val steamGuardCode = getSteamGuardCodeUseCase.invoke(sharedSecret).getOrThrow()
        val updateAuthSessionWithSteamGuardCodeRequest = UpdateAuthSessionWithSteamGuardCodeRequestDTO(
            steamId = steamId,
            clientId = beginAuthSessionViaCredentialsResponse.clientId,
            steamGuardCode = steamGuardCode
        )
        updateAuthSessionWithSteamGuardCodeUseCase.invoke(updateAuthSessionWithSteamGuardCodeRequest).getOrThrow()
        val pollAuthSessionStatusRequest = PollAuthSessionStatusRequestDTO(
            steamId = steamId,
            clientId = beginAuthSessionViaCredentialsResponse.clientId,
            requestId = beginAuthSessionViaCredentialsResponse.requestId,
            interval = beginAuthSessionViaCredentialsResponse.interval
        )
        val pollAuthSessionStatusResponse = pollAuthSessionStatusUseCase.invoke(pollAuthSessionStatusRequest).getOrThrow()
        pollAuthSessionStatusResponse.accessToken to pollAuthSessionStatusResponse.refreshToken
    }
}
