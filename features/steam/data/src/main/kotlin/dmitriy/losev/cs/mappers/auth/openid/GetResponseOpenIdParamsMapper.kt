package dmitriy.losev.cs.mappers.auth.openid

import dmitriy.losev.cs.dso.auth.openid.GetResponseOpenIdParamsDSO
import dmitriy.losev.cs.dto.auth.openid.GetResponseOpenIdParamsDTO
import org.koin.core.annotation.Factory

@Factory
class GetResponseOpenIdParamsMapper {

    fun map(value: GetResponseOpenIdParamsDSO): GetResponseOpenIdParamsDTO {
        return GetResponseOpenIdParamsDTO(
            action = value.action,
            mode = value.mode,
            openIdParams = value.openIdParams,
            nonce = value.nonce
        )
    }
}
