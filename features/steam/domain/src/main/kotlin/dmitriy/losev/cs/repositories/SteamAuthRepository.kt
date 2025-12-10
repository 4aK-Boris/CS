package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsRequestDTO
import dmitriy.losev.cs.dto.auth.BeginAuthSessionViaCredentialsResponseDTO
import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppRequestDTO
import dmitriy.losev.cs.dto.auth.GenerateAccessTokenForAppResponseDTO
import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyRequestDTO
import dmitriy.losev.cs.dto.auth.PasswordRSAPublicKeyResponseDTO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusRequestDTO
import dmitriy.losev.cs.dto.auth.PollAuthSessionStatusResponseDTO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeRequestDTO
import dmitriy.losev.cs.dto.auth.UpdateAuthSessionWithSteamGuardCodeResponseDTO
import dmitriy.losev.cs.dto.auth.openid.GetRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.GetResponseOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.PostRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.PostResponseOpenIdParamsDTO

interface SteamAuthRepository {

    suspend fun getPasswordRSAPublicKey(
        passwordRSAPublicKeyRequest: PasswordRSAPublicKeyRequestDTO
    ): PasswordRSAPublicKeyResponseDTO

    suspend fun beginAuthSessionViaCredentials(
        beginAuthSessionViaCredentialsRequest: BeginAuthSessionViaCredentialsRequestDTO
    ): BeginAuthSessionViaCredentialsResponseDTO

    suspend fun updateAuthSessionWithSteamGuardCode(
        updateAuthSessionWithSteamGuardCodeRequest: UpdateAuthSessionWithSteamGuardCodeRequestDTO
    ): UpdateAuthSessionWithSteamGuardCodeResponseDTO

    suspend fun pollAuthSessionStatus(
        pollAuthSessionStatusRequest: PollAuthSessionStatusRequestDTO
    ): PollAuthSessionStatusResponseDTO

    suspend fun generateAccessTokenForApp(
        generateAccessTokenForAppRequest: GenerateAccessTokenForAppRequestDTO
    ): GenerateAccessTokenForAppResponseDTO

    suspend fun getAuthRequestWithOpenId(getRequestOpenIdParams: GetRequestOpenIdParamsDTO): GetResponseOpenIdParamsDTO

    suspend fun postAuthRequestWithOpenId(postRequestOpenIdParams: PostRequestOpenIdParamsDTO): PostResponseOpenIdParamsDTO?
}
