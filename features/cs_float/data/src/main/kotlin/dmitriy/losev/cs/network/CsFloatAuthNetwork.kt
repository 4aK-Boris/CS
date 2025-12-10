package dmitriy.losev.cs.network

import dmitriy.losev.cs.dso.CsFloatAccountDSO
import dmitriy.losev.cs.dso.OpenIdParamsDSO

internal interface CsFloatAuthNetwork {

    suspend fun authWithOpenIdOnMainSite(openIdParams: OpenIdParamsDSO): String

    suspend fun authWithOpenId(openIdParams: OpenIdParamsDSO): CsFloatAccountDSO
}
