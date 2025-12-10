package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsResponseDSO
import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsRequestDSO
import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppRequestDSO
import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppResponseDSO
import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyResponseDSO
import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyRequestDSO
import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusRequestDSO
import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusResponseDSO
import dmitriy.losev.cs.dso.auth.SteamPoweredApiResponse
import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeResponseDSO
import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeRequestDSO
import dmitriy.losev.cs.dso.auth.openid.GetRequestOpenIdParamsDSO
import dmitriy.losev.cs.dso.auth.openid.PostRequestOpenIdParamsDSO

interface SteamAuthNetwork {

    suspend fun getPasswordRSAPublicKey(
        passwordRSAPublicKeyRequest: PasswordRSAPublicKeyRequestDSO
    ): SteamPoweredApiResponse<PasswordRSAPublicKeyResponseDSO>

    suspend fun beginAuthSessionViaCredentials(
        beginAuthSessionViaCredentialsRequest: BeginAuthSessionViaCredentialsRequestDSO
    ): SteamPoweredApiResponse<BeginAuthSessionViaCredentialsResponseDSO>

    suspend fun updateAuthSessionWithSteamGuardCode(
        updateAuthSessionWithSteamGuardCodeRequest: UpdateAuthSessionWithSteamGuardCodeRequestDSO
    ): SteamPoweredApiResponse<UpdateAuthSessionWithSteamGuardCodeResponseDSO>

    suspend fun pollAuthSessionStatus(
        pollAuthSessionStatusRequest: PollAuthSessionStatusRequestDSO
    ): SteamPoweredApiResponse<PollAuthSessionStatusResponseDSO>

    suspend fun generateAccessTokenForApp(
        generateAccessTokenForAppRequest: GenerateAccessTokenForAppRequestDSO
    ): SteamPoweredApiResponse<GenerateAccessTokenForAppResponseDSO>

    suspend fun getAuthRequestWithOpenId(getRequestOpenIdParams: GetRequestOpenIdParamsDSO): String

    suspend fun postAuthRequestWithOpenId(postRequestOpenIdParams: PostRequestOpenIdParamsDSO): String?
}
