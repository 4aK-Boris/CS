package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.core.OpenIdExtractor
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
import dmitriy.losev.cs.mappers.auth.BeginAuthSessionViaCredentialsRequestMapper
import dmitriy.losev.cs.mappers.auth.BeginAuthSessionViaCredentialsResponseMapper
import dmitriy.losev.cs.mappers.auth.GenerateAccessTokenForAppRequestMapper
import dmitriy.losev.cs.mappers.auth.GenerateAccessTokenForAppResponseMapper
import dmitriy.losev.cs.mappers.auth.PasswordRSAPublicKeyRequestMapper
import dmitriy.losev.cs.mappers.auth.PasswordRSAPublicKeyResponseMapper
import dmitriy.losev.cs.mappers.auth.PollAuthSessionStatusRequestMapper
import dmitriy.losev.cs.mappers.auth.PollAuthSessionStatusResponseMapper
import dmitriy.losev.cs.mappers.auth.UpdateAuthSessionWithSteamGuardCodeRequestMapper
import dmitriy.losev.cs.mappers.auth.UpdateAuthSessionWithSteamGuardCodeResponseMapper
import dmitriy.losev.cs.mappers.auth.openid.GetRequestOpenIdParamsMapper
import dmitriy.losev.cs.mappers.auth.openid.GetResponseOpenIdParamsMapper
import dmitriy.losev.cs.mappers.auth.openid.PostRequestOpenIdParamsMapper
import dmitriy.losev.cs.mappers.auth.openid.PostResponseOpenIdParamsMapper
import dmitriy.losev.cs.network.SteamAuthNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [SteamAuthRepository::class])
internal class SteamAuthRepositoryImpl(
    private val steamAuthNetwork: SteamAuthNetwork,
    private val openIdExtractor: OpenIdExtractor,
    private val passwordRSAPublicKeyRequestMapper: PasswordRSAPublicKeyRequestMapper,
    private val passwordRSAPublicKeyResponseMapper: PasswordRSAPublicKeyResponseMapper,
    private val beginAuthSessionViaCredentialsRequestMapper: BeginAuthSessionViaCredentialsRequestMapper,
    private val beginAuthSessionViaCredentialsResponseMapper: BeginAuthSessionViaCredentialsResponseMapper,
    private val updateAuthSessionWithSteamGuardCodeRequestMapper: UpdateAuthSessionWithSteamGuardCodeRequestMapper,
    private val updateAuthSessionWithSteamGuardCodeResponseMapper: UpdateAuthSessionWithSteamGuardCodeResponseMapper,
    private val pollAuthSessionStatusRequestMapper: PollAuthSessionStatusRequestMapper,
    private val pollAuthSessionStatusResponseMapper: PollAuthSessionStatusResponseMapper,
    private val generateAccessTokenForAppRequestMapper: GenerateAccessTokenForAppRequestMapper,
    private val generateAccessTokenForAppResponseMapper: GenerateAccessTokenForAppResponseMapper,
    private val getRequestOpenIdParamsMapper: GetRequestOpenIdParamsMapper,
    private val getResponseOpenIdParamsMapper: GetResponseOpenIdParamsMapper,
    private val postRequestOpenIdParamsMapper: PostRequestOpenIdParamsMapper,
    private val postResponseOpenIdParamsMapper: PostResponseOpenIdParamsMapper
) : SteamAuthRepository {

    override suspend fun getPasswordRSAPublicKey(
        passwordRSAPublicKeyRequest: PasswordRSAPublicKeyRequestDTO
    ): PasswordRSAPublicKeyResponseDTO {
        val passwordRSAPublicKeyRequest = passwordRSAPublicKeyRequestMapper.map(value = passwordRSAPublicKeyRequest)
        val response = steamAuthNetwork.getPasswordRSAPublicKey(passwordRSAPublicKeyRequest).response
        return passwordRSAPublicKeyResponseMapper.map(value = response)
    }

    override suspend fun beginAuthSessionViaCredentials(
        beginAuthSessionViaCredentialsRequest: BeginAuthSessionViaCredentialsRequestDTO
    ): BeginAuthSessionViaCredentialsResponseDTO {
        val beginAuthSessionViaCredentialsRequest = beginAuthSessionViaCredentialsRequestMapper.map(value = beginAuthSessionViaCredentialsRequest)
        val response = steamAuthNetwork.beginAuthSessionViaCredentials(beginAuthSessionViaCredentialsRequest).response
        return beginAuthSessionViaCredentialsResponseMapper.map(value = response)
    }

    override suspend fun updateAuthSessionWithSteamGuardCode(
        updateAuthSessionWithSteamGuardCodeRequest: UpdateAuthSessionWithSteamGuardCodeRequestDTO
    ): UpdateAuthSessionWithSteamGuardCodeResponseDTO {
        val updateAuthSessionWithSteamGuardCodeRequest = updateAuthSessionWithSteamGuardCodeRequestMapper.map(value = updateAuthSessionWithSteamGuardCodeRequest)
        val response = steamAuthNetwork.updateAuthSessionWithSteamGuardCode(updateAuthSessionWithSteamGuardCodeRequest).response
        return updateAuthSessionWithSteamGuardCodeResponseMapper.map(value = response)
    }

    override suspend fun pollAuthSessionStatus(
        pollAuthSessionStatusRequest: PollAuthSessionStatusRequestDTO
    ): PollAuthSessionStatusResponseDTO {
        val pollAuthSessionStatusRequest = pollAuthSessionStatusRequestMapper.map(value = pollAuthSessionStatusRequest)
        val response = steamAuthNetwork.pollAuthSessionStatus(pollAuthSessionStatusRequest).response
        return pollAuthSessionStatusResponseMapper.map(value = response)
    }

    override suspend fun generateAccessTokenForApp(
        generateAccessTokenForAppRequest: GenerateAccessTokenForAppRequestDTO
    ): GenerateAccessTokenForAppResponseDTO {
        val generateAccessTokenForAppRequest = generateAccessTokenForAppRequestMapper.map(value = generateAccessTokenForAppRequest)
        val response = steamAuthNetwork.generateAccessTokenForApp(generateAccessTokenForAppRequest).response
        return generateAccessTokenForAppResponseMapper.map(value = response)
    }

    override suspend fun getAuthRequestWithOpenId(getRequestOpenIdParams: GetRequestOpenIdParamsDTO): GetResponseOpenIdParamsDTO {
        val html = steamAuthNetwork.getAuthRequestWithOpenId(getRequestOpenIdParams = getRequestOpenIdParamsMapper.map(value = getRequestOpenIdParams))
        return getResponseOpenIdParamsMapper.map(value = openIdExtractor.extractOpenIdParamsFromHTML(html))
    }

    override suspend fun postAuthRequestWithOpenId(postRequestOpenIdParams: PostRequestOpenIdParamsDTO): PostResponseOpenIdParamsDTO? {
        val url = steamAuthNetwork.postAuthRequestWithOpenId(postRequestOpenIdParams = postRequestOpenIdParamsMapper.map(value = postRequestOpenIdParams))
        return url?.let { postResponseOpenIdParamsMapper.map(value = openIdExtractor.extractOpenIdParamsFromURL(url)) }
    }
}
