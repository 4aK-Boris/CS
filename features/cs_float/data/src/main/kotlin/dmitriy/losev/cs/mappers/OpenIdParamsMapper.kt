package dmitriy.losev.cs.mappers

import dmitriy.losev.cs.dso.OpenIdParamsDSO
import dmitriy.losev.cs.dto.OpenIdParamsDTO
import org.koin.core.annotation.Factory

@Factory
internal class OpenIdParamsMapper {

    fun map(value: OpenIdParamsDTO): OpenIdParamsDSO {
        return OpenIdParamsDSO(
            steamId = value.steamId,
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
