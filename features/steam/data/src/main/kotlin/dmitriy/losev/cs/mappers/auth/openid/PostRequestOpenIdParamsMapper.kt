package dmitriy.losev.cs.mappers.auth.openid

import dmitriy.losev.cs.core.MultiParmFormParamsCreator
import dmitriy.losev.cs.dso.auth.openid.PostRequestOpenIdParamsDSO
import dmitriy.losev.cs.dto.auth.openid.PostRequestOpenIdParamsDTO
import org.koin.core.annotation.Factory

@Factory
class PostRequestOpenIdParamsMapper(private val multiParmFormParamsCreator: MultiParmFormParamsCreator) {

    fun map(value: PostRequestOpenIdParamsDTO): PostRequestOpenIdParamsDSO {
        val (boundary, formText) = multiParmFormParamsCreator.createBoundaryAndFormText(openIdParams = value.openIdParams, nonce = value.nonce)
        return PostRequestOpenIdParamsDSO(
            steamId = value.steamId,
            boundary = boundary,
            formText = formText
        )
    }
}
