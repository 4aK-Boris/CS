package dmitriy.losev.cs.usecases.auth.openid

import dmitriy.losev.cs.dto.OpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.GetRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.GetResponseOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.PostRequestOpenIdParamsDTO
import dmitriy.losev.cs.dto.auth.openid.PostResponseOpenIdParamsDTO
import dmitriy.losev.cs.usecases.BaseUseCase
import dmitriy.losev.cs.usecases.auth.AuthWithOpenIdOnMainSiteUseCase
import dmitriy.losev.cs.usecases.auth.AuthWithOpenIdUseCase
import dmitriy.losev.cs.usecases.steam.account.active.tokens.UpdateSteamAccountCsFloatTokenUseCase
import org.koin.core.annotation.Factory

@Factory
class AuthWithOpenIdInOtherServicesUseCase(
    private val getAuthRequestWithOpenIdUseCase: GetAuthRequestWithOpenIdUseCase,
    private val postAuthRequestWithOpenIdUseCase: PostAuthRequestWithOpenIdUseCase,
    private val authWithOpenIdOnMainSiteUseCase: AuthWithOpenIdOnMainSiteUseCase,
    private val authWithOpenIdUseCase: AuthWithOpenIdUseCase,
    private val updateSteamAccountCsFloatTokenUseCase: UpdateSteamAccountCsFloatTokenUseCase
): BaseUseCase {

    suspend operator fun invoke(steamId: Long): Result<Long> = runCatching {
        val getRequestOpenIdParams = GetRequestOpenIdParamsDTO(steamId = steamId, realm = CS_FLOAT_REALM, returnTo = CS_FLOAT_RETURN_TO)
        val getResponseOpenIdParams = getAuthRequestWithOpenIdUseCase.invoke(getRequestOpenIdParams).getOrThrow()
        val postRequestOpenIdParams = getResponseOpenIdParams.toPostRequestOpenIdParams(steamId)
        val postResponseOpenIdParams = postAuthRequestWithOpenIdUseCase.invoke(postRequestOpenIdParams).getOrThrow()
        val openIdParams = postResponseOpenIdParams.toOpenIdParams(steamId)
        authWithOpenIdOnMainSiteUseCase.invoke(openIdParams).getOrThrow()
        authWithOpenIdUseCase.invoke(openIdParams).getOrThrow()
        updateSteamAccountCsFloatTokenUseCase.invoke(steamId).getOrThrow()
        steamId
    }

    private fun PostResponseOpenIdParamsDTO.toOpenIdParams(steamId: Long): OpenIdParamsDTO {
        return OpenIdParamsDTO(
            steamId = steamId,
            ns = ns,
            mode = mode,
            opEndpoint = opEndpoint,
            claimedId = claimedId,
            identity = identity,
            returnTo = returnTo,
            responseNonce = responseNonce,
            assocHandle = assocHandle,
            signed = signed,
            sig = sig
        )
    }

    private fun GetResponseOpenIdParamsDTO.toPostRequestOpenIdParams(steamId: Long): PostRequestOpenIdParamsDTO {
        return PostRequestOpenIdParamsDTO(
            steamId = steamId,
            action = action,
            mode = mode,
            openIdParams = openIdParams,
            nonce = nonce
        )
    }

    companion object {

        private const val CS_FLOAT_REALM = "https://csgofloat.com"
        private const val CS_FLOAT_RETURN_TO = "https://csgofloat.com/"
    }
}
