package dmitriy.losev.cs.repositories

import dmitriy.losev.cs.dto.CsFloatAccountDTO
import dmitriy.losev.cs.dto.OpenIdParamsDTO
import dmitriy.losev.cs.mappers.CsFloatAccountMapper
import dmitriy.losev.cs.mappers.OpenIdParamsMapper
import dmitriy.losev.cs.network.CsFloatAuthNetwork
import org.koin.core.annotation.Factory

@Factory(binds = [CsFloatAuthRepository::class])
internal class CsFloatAuthRepositoryImpl(
    private val csFloatAuthNetwork: CsFloatAuthNetwork,
    private val openIdParamsMapper: OpenIdParamsMapper,
    private val csFloatAccountMapper: CsFloatAccountMapper
) : CsFloatAuthRepository {

    override suspend fun authWithOpenIdOnMainSite(openIdParams: OpenIdParamsDTO): String {
        return csFloatAuthNetwork.authWithOpenIdOnMainSite(openIdParams = openIdParamsMapper.map(value = openIdParams))
    }

    override suspend fun authWithOpenId(openIdParams: OpenIdParamsDTO): CsFloatAccountDTO {
        return csFloatAccountMapper.map(value = csFloatAuthNetwork.authWithOpenId(openIdParams = openIdParamsMapper.map(value = openIdParams)))
    }
}
