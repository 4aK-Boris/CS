package dmitriy.losev.cs.network

import dmitriy.losev.cs.clients.SteamCommunityProxyClient
import dmitriy.losev.cs.clients.SteamPoweredProxyClient
import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsResponseDSO
import dmitriy.losev.cs.dso.auth.BeginAuthSessionViaCredentialsRequestDSO
import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppRequestDSO
import dmitriy.losev.cs.dso.auth.GenerateAccessTokenForAppResponseDSO
import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyResponseDSO
import dmitriy.losev.cs.dso.auth.PasswordRSAPublicKeyRequestDSO
import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusRequestDSO
import dmitriy.losev.cs.dso.auth.PollAuthSessionStatusResponseDSO
import dmitriy.losev.cs.dso.auth.SteamPoweredApiResponse
import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeRequestDSO
import dmitriy.losev.cs.dso.auth.UpdateAuthSessionWithSteamGuardCodeResponseDSO
import dmitriy.losev.cs.dso.auth.openid.GetRequestOpenIdParamsDSO
import dmitriy.losev.cs.dso.auth.openid.PostRequestOpenIdParamsDSO
import org.koin.core.annotation.Provided
import org.koin.core.annotation.Singleton

@Singleton(binds = [SteamAuthNetwork::class])
class SteamAuthNetworkImpl(
    @Provided private val steamPoweredProxyClient: SteamPoweredProxyClient,
    @Provided private val steamCommunityProxyClient: SteamCommunityProxyClient
) : SteamAuthNetwork {

    override suspend fun getPasswordRSAPublicKey(
        passwordRSAPublicKeyRequest: PasswordRSAPublicKeyRequestDSO
    ): SteamPoweredApiResponse<PasswordRSAPublicKeyResponseDSO> {
        return steamPoweredProxyClient.get(
            steamId = passwordRSAPublicKeyRequest.steamId,
            handle = GET_PASSWORD_RSA_PUBLIC_KEY_HANDLE,
            params = mapOf("account_name" to passwordRSAPublicKeyRequest.login)
        )
    }

    override suspend fun beginAuthSessionViaCredentials(
        beginAuthSessionViaCredentialsRequest: BeginAuthSessionViaCredentialsRequestDSO
    ): SteamPoweredApiResponse<BeginAuthSessionViaCredentialsResponseDSO> {
        return steamPoweredProxyClient.postWithUrlEncodedForm(
            steamId = beginAuthSessionViaCredentialsRequest.steamId,
            handle = BEGIN_AUTH_SESSION_VIA_CREDENTIALS,
            formParams = mapOf(
                "account_name" to beginAuthSessionViaCredentialsRequest.login,
                "encrypted_password" to beginAuthSessionViaCredentialsRequest.encryptedPassword,
                "encryption_timestamp" to beginAuthSessionViaCredentialsRequest.timeStamp,
                "remember_login" to "true",
                "persistence" to "1",
                "platform_type" to "3",
                "device_friendly_name" to "Xiaomi 15"
            )
        )
    }

    override suspend fun updateAuthSessionWithSteamGuardCode(
        updateAuthSessionWithSteamGuardCodeRequest: UpdateAuthSessionWithSteamGuardCodeRequestDSO
    ): SteamPoweredApiResponse<UpdateAuthSessionWithSteamGuardCodeResponseDSO> {
        return steamPoweredProxyClient.postWithUrlEncodedForm(
            steamId = updateAuthSessionWithSteamGuardCodeRequest.steamId,
            handle = UPDATE_AUTH_SESSION_WITH_STEAM_GUARD_CODE,
            formParams = mapOf(
                "client_id" to updateAuthSessionWithSteamGuardCodeRequest.clientId,
                "steamid" to updateAuthSessionWithSteamGuardCodeRequest.steamIdString,
                "code" to updateAuthSessionWithSteamGuardCodeRequest.steamGuardCode,
                "code_type" to updateAuthSessionWithSteamGuardCodeRequest.codeType
            )
        )
    }

    override suspend fun pollAuthSessionStatus(
        pollAuthSessionStatusRequest: PollAuthSessionStatusRequestDSO
    ): SteamPoweredApiResponse<PollAuthSessionStatusResponseDSO> {
        return steamPoweredProxyClient.postWithUrlEncodedForm(
            steamId = pollAuthSessionStatusRequest.steamId,
            handle = POLL_AUTH_SESSION_STATE,
            formParams = mapOf(
                "client_id" to pollAuthSessionStatusRequest.clientId,
                "request_id" to pollAuthSessionStatusRequest.requestId,
                "interval" to pollAuthSessionStatusRequest.interval
            )
        )
    }

    override suspend fun generateAccessTokenForApp(
        generateAccessTokenForAppRequest: GenerateAccessTokenForAppRequestDSO
    ): SteamPoweredApiResponse<GenerateAccessTokenForAppResponseDSO> {
        return steamPoweredProxyClient.postWithUrlEncodedForm(
            steamId = generateAccessTokenForAppRequest.steamId,
            handle = GENERATE_ACCESS_TOKEN_FOR_APP,
            formParams = mapOf(
                "refresh_token" to generateAccessTokenForAppRequest.refreshToken,
                "steamid" to generateAccessTokenForAppRequest.steamIdString
            )
        )
    }

    override suspend fun getAuthRequestWithOpenId(getRequestOpenIdParams: GetRequestOpenIdParamsDSO): String {
        return steamCommunityProxyClient.getWithTextBody(
            steamId = getRequestOpenIdParams.steamId,
            handle = AUTH_REQUEST_WITH_OPEN_ID_HANDLE,
            params = getRequestOpenIdParams.convertToParameters()
        )
    }

    override suspend fun postAuthRequestWithOpenId(postRequestOpenIdParams: PostRequestOpenIdParamsDSO): String? {
        return steamCommunityProxyClient.postWithUrlMultiParmFromAndReturnLocation(
            steamId = postRequestOpenIdParams.steamId,
            handle = AUTH_REQUEST_WITH_OPEN_ID_HANDLE,
            boundary = postRequestOpenIdParams.boundary,
            formText = postRequestOpenIdParams.formText
        )
    }

    companion object {
        private const val GET_PASSWORD_RSA_PUBLIC_KEY_HANDLE = "/IAuthenticationService/GetPasswordRSAPublicKey/v1"
        private const val BEGIN_AUTH_SESSION_VIA_CREDENTIALS = "/IAuthenticationService/BeginAuthSessionViaCredentials/v1/"
        private const val UPDATE_AUTH_SESSION_WITH_STEAM_GUARD_CODE = "/IAuthenticationService/UpdateAuthSessionWithSteamGuardCode/v1"
        private const val POLL_AUTH_SESSION_STATE = "/IAuthenticationService/PollAuthSessionStatus/v1"
        private const val GENERATE_ACCESS_TOKEN_FOR_APP = "/IAuthenticationService/GenerateAccessTokenForApp/v1"
        private const val AUTH_REQUEST_WITH_OPEN_ID_HANDLE = "/openid/login"
    }
}
