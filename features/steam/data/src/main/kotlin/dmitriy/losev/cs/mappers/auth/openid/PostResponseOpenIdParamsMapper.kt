package dmitriy.losev.cs.mappers.auth.openid

import dmitriy.losev.cs.dso.auth.openid.PostResponseOpenIdParamsDSO
import dmitriy.losev.cs.dto.auth.openid.PostResponseOpenIdParamsDTO
import org.koin.core.annotation.Factory

@Factory
class PostResponseOpenIdParamsMapper {

    fun map(value: PostResponseOpenIdParamsDSO): PostResponseOpenIdParamsDTO {
        return PostResponseOpenIdParamsDTO(
            ns = value.ns,
            mode = value.mode,
            opEndpoint = value.opEndpoint,
            claimedId = value.claimedId,
            identity = value.identity,
            returnTo = value.returnTo,
            responseNonce = value.responseNonce,
            assocHandle = value.assocHandle,
            signed = value.signed,
            sig = value.sig
        )
    }
}
