package dmitriy.losev.ozon.repository

import dmitriy.losev.ozon.core.InspectLinkParser
import dmitriy.losev.ozon.dto.InspectParamsDTO
import dmitriy.losev.ozon.mappers.InspectParamsMapper
import dmitriy.losev.ozon.repositories.InspectRepository
import org.koin.core.annotation.Factory

@Factory(binds = [InspectRepository::class])
class InspectRepositoryImpl(
    private val inspectLinkParser: InspectLinkParser,
    private val inspectParamsMapper: InspectParamsMapper
): InspectRepository {

    override suspend fun decodeLink(link: String): InspectParamsDTO? {
        return inspectParamsMapper.map(value = inspectLinkParser.parse(link))
    }
}