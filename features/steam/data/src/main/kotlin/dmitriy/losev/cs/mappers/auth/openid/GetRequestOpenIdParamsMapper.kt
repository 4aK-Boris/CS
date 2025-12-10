package dmitriy.losev.cs.mappers.auth.openid

import dmitriy.losev.cs.dso.auth.openid.GetRequestOpenIdParamsDSO
import dmitriy.losev.cs.dto.auth.openid.GetRequestOpenIdParamsDTO
import org.koin.core.annotation.Factory

@Factory
class GetRequestOpenIdParamsMapper {

    fun map(value: GetRequestOpenIdParamsDTO): GetRequestOpenIdParamsDSO {
        return GetRequestOpenIdParamsDSO(
            steamId = value.steamId,
            claimedId = value.claimedId,
            identity = value.identity,
            mode = value.mode,
            ns = value.ns,
            realm = value.realm,
            returnTo = value.returnTo
        )
    }
}
