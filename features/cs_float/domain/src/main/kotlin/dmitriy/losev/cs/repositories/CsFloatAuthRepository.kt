package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.CsFloatAccountDTO
import dmitriy.losev.cs.dto.OpenIdParamsDTO

interface CsFloatAuthRepository {

    suspend fun authWithOpenIdOnMainSite(openIdParams: OpenIdParamsDTO): String

    suspend fun authWithOpenId(openIdParams: OpenIdParamsDTO): CsFloatAccountDTO
}
